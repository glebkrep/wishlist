package com.hotelki.wishlist.Repository

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "wish_item_table")
data class WishItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,

    @ColumnInfo(name = "item_name")
    var name:String,

    @ColumnInfo(name = "item_image")
    var imageResId:String?=null,

    @ColumnInfo(name = "item_description")
    var description:String?=null,

    @ColumnInfo(name = "item_store")
    var store:String?=null,

    @ColumnInfo(name = "item_link")
    var link:String?=null,

    @ColumnInfo(name = "item_price")
    var price:Double= Double.NaN,

    @ColumnInfo(name = "item_date_posted")
    val date_posted:Long = System.currentTimeMillis(),

    @ColumnInfo(name = "item_is_archived")
    var isArchived:Boolean = false,

    @ColumnInfo(name = "item_date_archived")
    var date_archived: Long = 0L,

    @ColumnInfo(name = "item_image_date_changed")
    var image_changed_date:Long = 0L
):Parcelable