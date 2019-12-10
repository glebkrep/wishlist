package com.hotelki.wishlist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(WishItem::class),version = 1)
public abstract class WishItemRoomDatabase():RoomDatabase() {

    abstract fun wishItemDao():WishItemDao

    companion object{
        @Volatile
        private var INSTANCE:WishItemRoomDatabase? = null

        fun getDatabase(context:Context,scope:CoroutineScope):WishItemRoomDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            else{
                synchronized(this){
                    val instance = Room.databaseBuilder(context.applicationContext,
                        WishItemRoomDatabase::class.java,"wish_database").build()
                    INSTANCE = instance
                    return instance
                }

            }
        }
    }
}