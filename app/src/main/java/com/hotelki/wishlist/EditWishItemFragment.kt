package com.hotelki.wishlist

import android.app.Activity
import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_edit_wish_item.*
import java.lang.ref.WeakReference
import java.security.Permissions
import java.util.jar.Manifest


class EditWishItemFragment : Fragment() {
    lateinit var wishItem: WishItem
    lateinit var viewModel:MainActivityViewModel
    lateinit var imageView:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        //TODO: questionable as well
        viewModel = MainActivity.obtainViewModel(activity!!)
        return inflater.inflate(R.layout.fragment_edit_wish_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishItem = arguments!!["wishItem"] as WishItem

        imageView = itemFragmentImage
        itemFragmentNameEdit.setText(wishItem.name)
        //TODO: set image
        if (!wishItem.imageResId.isNullOrEmpty()){
            imageView.setImageURI(Uri.parse(wishItem.imageResId))
        }
        if(wishItem.description.isNullOrEmpty()){

        }
        else{
            itemFragmentDescriptionEdit.setText(wishItem.description)
        }
        if (wishItem.store.isNullOrEmpty()){

        }
        else{
            itemFragmentStoreEdit.setText(wishItem.store)
        }
        if (wishItem.link.isNullOrEmpty()){

        }
        else{
            itemFragmentLinkEdit.setText(wishItem.store)
        }
        if (wishItem.price.isNaN()){

        }
        else{
            //TODO: multilingual currancy
            itemFragmentPriceEdit.setText(wishItem.price.toString()+" $")
        }

        imageView.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (context!!.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
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

        itemFragmentSaveButton.setOnClickListener {
            //TODO: add image here
            var newWishItem = WishItem(
                id = wishItem.id,
                name = itemFragmentNameEdit.text.toString(),
                description = itemFragmentDescriptionEdit.text.toString(),
                store = itemFragmentStoreEdit.text.toString(),
                link = itemFragmentLinkEdit.text.toString(),
                price = itemFragmentPriceEdit.text.toString().split(" ")[0].toDouble()
            )
            viewModel.update(newWishItem)
            findNavController().navigate(R.id.action_editWishItemFragment_to_wishListFragment)
        }


    }
    fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
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

            val wr:WeakReference<Context> = WeakReference(context!!.applicationContext)

            viewModel.copyImageToInternalStorage(data?.data,wr,wishItem.id)


            //imageView.setImageURI(data?.data)
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
