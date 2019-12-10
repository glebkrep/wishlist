package com.hotelki.wishlist.Repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishItemDao{

    @Query("select * from wish_item_table order by item_date_posted desc")
    fun getAllWishesDesc():LiveData<List<WishItem>>

    @Insert
    suspend fun insert(wishItem: WishItem)

    @Query("update wish_item_table SET item_name=:newName, item_description=:newDescription, item_store=:newStore,item_link=:newLink,item_price=:newPrice where id=:id")
    suspend fun update(id:Int,newName:String,newDescription:String?,newStore:String?,newLink:String?,
                       newPrice:Double?)

    @Query("delete from wish_item_table where id=:id")
    suspend fun deleteById(id:Int)

    @Query("delete from wish_item_table")
    suspend fun deleteAll()

    @Query("update wish_item_table set item_image=:newImageURI, item_image_date_changed=:newImageDate where id=:id")
    suspend fun changeImage(id:Int,newImageURI:String,newImageDate:Long=System.currentTimeMillis())

}