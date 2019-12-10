package com.hotelki.wishlist

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.hotelki.wishlist.Repository.WishItem
import com.hotelki.wishlist.Repository.WishItemRepository
import com.hotelki.wishlist.Repository.WishItemRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.WeakReference

class MainActivityViewModel(application:Application):AndroidViewModel(application) {
    private val repository: WishItemRepository
    val allWishItems:LiveData<List<WishItem>>

    init {
        val wishItemDao = WishItemRoomDatabase.getDatabase(application,viewModelScope).wishItemDao()
        repository = WishItemRepository(wishItemDao)
        allWishItems = repository.allWishItems
    }

    fun insert(wishItem: WishItem)= viewModelScope.launch {
        repository.insert(wishItem)
    }

    fun deleteById(id:Int) = viewModelScope.launch {
        repository.deleteById(id)
    }

    fun update(newWishItem: WishItem) = viewModelScope.launch {
        repository.update(newWishItem.id,newWishItem.name,newWishItem.description,newWishItem.store,newWishItem.link,newWishItem.price)
    }

    fun changeImage(id:Int,newImageURI:String) = viewModelScope.launch {
        repository.changeImage(id,newImageURI)
    }

    fun copyImageToInternalStorage(uri:Uri?,mContext:WeakReference<Context>,wishItemId:Int) = viewModelScope.launch{
        withContext(Dispatchers.IO){

            val uri = uri
            val requestOptions = RequestOptions().override(400)
                .downsample(DownsampleStrategy.CENTER_INSIDE)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

            mContext.get()?.let {
                val bitmap = Glide.with(it)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .submit()
                    .get()
                try {

                    var file = File(it.filesDir, "WishItemImages")
                    if (!file.exists()) {
                        file.mkdir()
                    }
                    file = File(file, "img$wishItemId.jpg")
                    val out = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out)
                    out.flush()
                    out.close()
                    val newUri = file.toUri()
                    changeImage(wishItemId,newImageURI = newUri.toString())


                    Log.i("MainActivityViewModel", "Image saved., old uri ${uri.toString()}, new uri ${newUri.toString()}")
                } catch (e: Exception) {
                    Log.i("MainActivityViewModel", "Failed to save image.")
                }
            }
        }

    }





}