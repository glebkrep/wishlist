package com.hotelki.wishlist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_wish_list.*

class WishListFragment : Fragment() {
    lateinit var viewModel:MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        this.findNavController().navigate(R.id.action_wishListFragment_to_wishItemFragment)

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO: questionable
        viewModel = MainActivity.obtainViewModel(activity!!)
        return inflater.inflate(R.layout.fragment_wish_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WishListAdapter(context,this)


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

//
//        viewModel.insert(WishItem(name = "hay wassup",price = 123.0,description = "dada,da"))


        //TODO:question mark
        viewModel.allWishItems.observe(this, Observer {wishItems->
            wishItems.let { adapter.setWishList(it) }
        })

    }


    override fun onDetach() {
        super.onDetach()
    }


}
