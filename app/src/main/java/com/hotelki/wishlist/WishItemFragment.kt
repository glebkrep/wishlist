package com.hotelki.wishlist


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_wish_item.*

/**
 * A simple [Fragment] subclass.
 */
class WishItemFragment : Fragment() {
    lateinit var wishItem:WishItem
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishItem = arguments!!["wishItem"] as WishItem


        itemFragmentName.text = wishItem.name
        //TODO: set image
        if (!wishItem.imageResId.isNullOrEmpty()){
            itemFragmentImage.setImageURI(Uri.parse(wishItem.imageResId))
        }
        if(wishItem.description.isNullOrEmpty()){
            _description.visibility = View.INVISIBLE
        }
        else{
            itemFragmentDescription.text = wishItem.description
        }
        if (wishItem.store.isNullOrEmpty()){
            _store.visibility = View.INVISIBLE
        }
        else{
            itemFragmentStore.text = wishItem.store
        }
        if (wishItem.link.isNullOrEmpty()){
            _link.visibility = View.INVISIBLE
        }
        else{
            itemFragmentLink.text = wishItem.store
        }
        if (wishItem.price.isNaN()){
            _price.visibility = View.INVISIBLE
        }
        else{
            //TODO: multilingual currancy
            itemFragmentPrice.text = wishItem.price.toString()+" $"
        }


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
