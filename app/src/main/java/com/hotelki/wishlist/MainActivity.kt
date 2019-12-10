package com.hotelki.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {


    init {
        instance = this
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    //TODO: questionable
    //Static
    companion object{
        private var instance:MainActivity?= null
        public fun obtainViewModel(activity:FragmentActivity):MainActivityViewModel {
            // Use a Factory to inject dependencies into the ViewModel
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())

            return ViewModelProvider(instance!!,factory).get(MainActivityViewModel::class.java)

        }
    }

}
