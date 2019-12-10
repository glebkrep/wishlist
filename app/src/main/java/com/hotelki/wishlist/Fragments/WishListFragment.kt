package com.hotelki.wishlist.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hotelki.wishlist.MainActivity
import com.hotelki.wishlist.MainActivityViewModel
import com.hotelki.wishlist.R
import com.hotelki.wishlist.WishListAdapter
import kotlinx.android.synthetic.main.fragment_wish_list.*

class WishListFragment : Fragment() {
    lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val adapter = WishListAdapter(context, this)


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


        floatingActionButton.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_wishListFragment_to_addWishItemFragment)
        }

        //TODO:question mark
        viewModel.allWishItems.observe(this, Observer {wishItems->
            wishItems.let { adapter.setWishList(it) }
        })

    }


    override fun onDetach() {
        super.onDetach()
    }


}
