package dev.samuelmcmurray.e_wastemanagement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item


class CompanyBidsAdapter(
    private var context: Context,
    private var items: List<Item>
) :
    RecyclerView.Adapter<CompanyBidsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_img_company)
        var itemName: TextView = itemView.findViewById(R.id.item_name_company)
        var companyName: TextView = itemView.findViewById(R.id.companyName_company)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_card_company_detail, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = items[position].name
        val description = items[position].description
        val parts = description.split("::")
        val compName = parts[1].toString()
        val price = parts[2]
        holder.companyName.text = "Bid by: $compName"
        val imageUri = Uri.parse(items[position].image1)
        Glide.with(context).load(imageUri).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}