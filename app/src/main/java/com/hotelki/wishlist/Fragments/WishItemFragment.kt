package com.hotelki.wishlist.Fragments


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hotelki.wishlist.R
import com.hotelki.wishlist.Repository.WishItem
import com.hotelki.wishlist.Utils.MyGlideUtils
import com.hotelki.wishlist.Utils.MyUtils
import kotlinx.android.synthetic.main.fragment_wish_item.*
import kotlinx.android.synthetic.main.wish_list_item_view.*


class WishItemFragment : Fragment() {
    lateinit var wishItem: WishItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishItem = arguments!!["wishItem"] as WishItem


        itemFragmentName.text = wishItem.name


        MyGlideUtils.displayImage(this,Uri.parse(wishItem.imageResId),wishItem.image_changed_date,itemFragmentImage)

        MyUtils.fillViews(wishItem,itemFragmentName,itemFragmentPrice,itemFragmentDescription,itemFragmentStore,itemFragmentLink,false)



        itemFragmentEditButton.setOnClickListener {
            val argumentsBundle:Bundle = Bundle()
            argumentsBundle.putParcelable("wishItem",wishItem)
            findNavController().navigate(R.id.action_wishItemFragment_to_editWishItemFragment,argumentsBundle)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wish_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}
