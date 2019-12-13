package com.hotelki.wishlist.Fragments

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
import com.hotelki.wishlist.MainActivity
import com.hotelki.wishlist.MainActivityViewModel
import com.hotelki.wishlist.R
import com.hotelki.wishlist.Repository.WishItem
import com.hotelki.wishlist.Utils.MyGlideUtils
import com.hotelki.wishlist.Utils.MyUtils
import kotlinx.android.synthetic.main.fragment_edit_wish_item.*
import java.lang.ref.WeakReference


class EditWishItemFragment : Fragment() {
    lateinit var wishItem: WishItem
    lateinit var viewModel: MainActivityViewModel
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

        //Load image
        MyGlideUtils.displayImage(this,wishItem.imageResId,wishItem.image_changed_date,itemFragmentImage)
        //Fill all the views
        MyUtils.fillViews(wishItem,itemFragmentNameEdit,itemFragmentPriceEdit,itemFragmentDescriptionEdit,itemFragmentStoreEdit,itemFragmentLinkEdit,true)



        imageView.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (context!!.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
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
            val wr:WeakReference<Context> = WeakReference(context!!.applicationContext)
            viewModel.copyImageToInternalStorage(data?.data,wr,wishItem.id)

            //TODO: show image
            imageView.setImageURI(data?.data)

        }
    }

    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }
}
