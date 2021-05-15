package dev.samuelmcmurray.e_wastemanagement.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.ui.itemDetail.ItemFragment


class ProfileAdapter(
    private var context: Context,
    private var items: List<Item>,
    private var view: View
) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_img_profile)
        var itemName: TextView = itemView.findViewById(R.id.item_name_profile)
        var cardViewItem: CardView = itemView.findViewById(R.id.cardview_clickable_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_card_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = items[position].name
        val imageUri = Uri.parse(items[position].image1)
        Glide.with(context).load(imageUri).into(holder.image)

        // click on cardView should load a new fragment with more info on the item
        holder.cardViewItem.setOnClickListener { v ->
            val item = items[position]
            val appCompatActivity: AppCompatActivity = view.context as AppCompatActivity
            val itemFragment = ItemFragment(item)
            appCompatActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.profileFragment, itemFragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}