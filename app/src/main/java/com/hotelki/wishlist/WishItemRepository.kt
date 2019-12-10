package com.hotelki.wishlist

import androidx.lifecycle.LiveData

class WishItemRepository(private val wishItemDao: WishItemDao){

    val allWishItems:LiveData<List<WishItem>> = wishItemDao.getAllWishesDesc()

    suspend fun insert(wishItem:WishItem){
        wishItemDao.insert(wishItem)
    }

    suspend fun deleteById(id:Int){
        wishItemDao.deleteById(id)
    }

    suspend fun update(id:Int,newName:String,newDescription:String?,newStore:String?,newLink:String?,newPrice:Double?){
        wishItemDao.update(id,newName,newDescription,newStore,newLink,newPrice)
    }

    suspend fun changeImage(id:Int,imageURI:String){
        wishItemDao.changeImage(id,imageURI)
    }
}