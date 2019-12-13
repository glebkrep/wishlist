package com.hotelki.wishlist

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hotelki.wishlist.Repository.WishItem
import com.hotelki.wishlist.Utils.MyGlideUtils

class WishListAdapter internal constructor(val context: Context?,val parentFragment: Fragment) : RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var wishList = emptyList<WishItem>()

    class WishListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val wishItemImageView:ImageView = itemView.findViewById(R.id.wishItemImageView)
        val wishItemNameTextView:TextView = itemView.findViewById(R.id.wishItemNameTextView)
        val wishItemPriceTextView:TextView = itemView.findViewById(R.id.wishItemPriceTextView)
        val wishItemDescriptionTextView:TextView = itemView.findViewById(R.id.wishItemDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val itemView = inflater.inflate(R.layout.wish_list_item_view,parent,false)
        return WishListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        val current = wishList[position]

        if (current.imageResId.isNullOrEmpty()){
            holder.wishItemImageView.visibility = View.GONE

        }
        else{
            holder.wishItemImageView.visibility = View.VISIBLE
            val uri = Uri.parse(current.imageResId)
            MyGlideUtils.displayImage(parentFragment,uri,current.image_changed_date,holder.wishItemImageView)
        }




        holder.wishItemNameTextView.text = current.name
        //TODO: multilangual currency
        holder.wishItemPriceTextView.text = current.price.toString()+" $"

        if (current.description.isNullOrEmpty()){
            holder.wishItemDescriptionTextView.text = " "
        }
        else{
            holder.wishItemDescriptionTextView.text = current.description
        }

        holder.itemView.setOnClickListener {
            val argumentsBundle:Bundle = Bundle()
            argumentsBundle.putParcelable("wishItem",current)
            findNavController(parentFragment).navigate(R.id.action_wishListFragment_to_wishItemFragment,argumentsBundle)
        }



    }

    internal fun setWishList(wishList:List<WishItem>){
        this.wishList = wishList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return wishList.size
    }


}
