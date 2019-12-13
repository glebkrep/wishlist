package com.hotelki.wishlist.Utils

import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.MediaStoreSignature
import com.hotelki.wishlist.R

class MyGlideUtils() {
    companion object{
        fun displayImage(fragment: Fragment,load:Uri?,dateChanged:Long,into:ImageView ){
            Glide.with(fragment)
                .load(load)
                .fitCenter()
                //.cropCenter()
                .signature(MediaStoreSignature("JPEG",dateChanged,1))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(into)

        }
        fun displayImage(fragment: Fragment,load:String?,dateChanged:Long,into:ImageView ){
            if (load == null){
                Glide.with(fragment)
                    .load(R.drawable.ic_launcher_background)
                    .fitCenter()
                    //.cropCenter()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(into)
            }
            else{
                displayImage(fragment,Uri.parse(load),dateChanged,into)
            }

        }
    }
}