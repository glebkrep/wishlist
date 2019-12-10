package com.hotelki.wishlist.Utils

import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.hotelki.wishlist.Repository.WishItem

class MyUtils(){
    companion object{
        fun fillViews(wishItem: WishItem, nameView: View,priceView:View,descriptionView:View,storeView:View,linkView:View,isEdit:Boolean){
            if (isEdit){
                val name = nameView as EditText
                val price = priceView as EditText
                val description = descriptionView as EditText
                val store = storeView as EditText
                val link = linkView as EditText

                if(!wishItem.name.isNullOrEmpty()) name.setText(wishItem.name)
                if(!wishItem.description.isNullOrEmpty()) description.setText(wishItem.description)

                if(!wishItem.price.isNaN()) price.setText(wishItem.price.toString())
                if(!wishItem.store.isNullOrEmpty()) store.setText(wishItem.store)
                if(!wishItem.link.isNullOrEmpty()) link.setText(wishItem.link)


            }

            else{
                val name = nameView as TextView
                val price = priceView as TextView
                val description = descriptionView as TextView
                val store = storeView as TextView
                val link = linkView as TextView

                if(!wishItem.name.isNullOrEmpty()) name.setText(wishItem.name)
                if(!wishItem.description.isNullOrEmpty()) description.setText(wishItem.description)

                if(!wishItem.price.isNaN()) price.setText(wishItem.price.toString()+"$")
                if(!wishItem.store.isNullOrEmpty()) store.setText(wishItem.store)
                if(!wishItem.link.isNullOrEmpty()) link.setText(wishItem.link)

            }
        }
    }
}