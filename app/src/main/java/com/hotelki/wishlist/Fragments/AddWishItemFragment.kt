package com.hotelki.wishlist.Fragments


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.hotelki.wishlist.MainActivity
import com.hotelki.wishlist.MainActivityViewModel
import com.hotelki.wishlist.R
import com.hotelki.wishlist.Repository.WishItem
import kotlinx.android.synthetic.main.fragment_add_wish_item.*

/**
 * A simple [Fragment] subclass.
 */
class AddWishItemFragment : Fragment() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var imageView: ImageView
    lateinit var imageUri:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //TODO: questionable as well
        viewModel = MainActivity.obtainViewModel(activity!!)
        return inflater.inflate(R.layout.fragment_add_wish_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUri = ""
        imageView = addItemFragmentImage
        imageView.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (context!!.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                        PERMISSION_CODE
                    )
                }
                else{
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else{
                pickImageFromGallery()
            }
        }
        itemFragmentAddButton.setOnClickListener {
            //TODO: add image here
            var name:String = ""
            var description:String = ""
            var store:String=""
            var link:String=""
            var price:Double =0.0


            if(!addItemFragmentName.text.isNullOrEmpty()) name = addItemFragmentName.text.toString()
            if(!addItemFragmentDescription.text.isNullOrEmpty()) description = addItemFragmentDescription.text.toString()
            if(!addItemFragmentStore.text.isNullOrEmpty()) store = addItemFragmentStore.text.toString()
            if(!addItemFragmentLink.text.isNullOrEmpty()) link = addItemFragmentLink.text.toString()
            if(!addItemFragmentPrice.text.isNullOrEmpty()) price = addItemFragmentPrice.text.toString().toDouble()

            if (addItemFragmentName.text.isNullOrEmpty()){
                //TODO:bad coding
                Toast.makeText(context,"Enter name at least...",Toast.LENGTH_SHORT).show()
            }
            else{
                var newWishItem:WishItem
                if (imageUri==""){
                     newWishItem = WishItem(
                        name = name,
                        description = description,
                        store = store,
                        link = link,
                        price = price
                    )
                }
                else{
                    newWishItem = WishItem(
                        name = name,
                        description = description,
                        store = store,
                        link = link,
                        price = price,
                        imageResId = imageUri
                    )
                }
                viewModel.insert(newWishItem)
                findNavController().navigate(R.id.action_addWishItemFragment_to_wishListFragment)
            }

        }


    }
    fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
    }


    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            //TODO: save image to internalStorage and return new uri
//
//            val wr: WeakReference<Context> = WeakReference(context!!.applicationContext)
//
//            viewModel.copyImageToInternalStorage(data?.data,wr,wishItem.id)


            imageUri = data!!.data!!.toString()

            //TODO: change to glide
            imageView.setImageURI(data?.data)
            //viewModel.changeImage(wishItem.id,data?.data.toString())

        }
    }

    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }
}
