package dev.samuelmcmurray.e_wastemanagement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RecyclerViewAdapter(private var context: Context, private var items: List<Item>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_img)
        var itemName: TextView = itemView.findViewById(R.id.item_name)
        var cardViewItem: CardView = itemView.findViewById(R.id.cardview_clickable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = items[position].name
        Glide.with(context).load(items[position].imageURI).into(holder.image)
//        val ischecked = if (position === checkedposition) true else false
//
//        holder.tv.setText(productList.get(position))
//        holder.tv.setChecked(lastSelectedPosition === position)
//        selectionState.setOnClickListener(View.OnClickListener {
//            lastSelectedPosition = getAdapterPosition()
//            notifyDataSetChanged()
//            Toast.makeText(this@OffersRecyclerViewAdapter.context,
//                    "selected offer is " + offerName.getText(),
//                    Toast.LENGTH_LONG).show()
//        })

        // click on cardView should load a new fragment with more info on the item


        holder.cardViewItem.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //  TODO("Not yet implemented")
            }

        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

}