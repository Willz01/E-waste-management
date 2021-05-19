package dev.samuelmcmurray.e_wastemanagement.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item


class CompanyBidsAdapter(
    private var context: Context,
    private var items: List<Item>, var view: View
) :
    RecyclerView.Adapter<CompanyBidsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.item_img_company)
        var itemName: TextView = itemView.findViewById(R.id.item_name_company)
        var companyName: TextView = itemView.findViewById(R.id.companyName_company)
        var cardView: CardView = itemView.findViewById(R.id.cardview_clickable_company)
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

        holder.cardView.setOnClickListener { v ->
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Accept bif from $compName with asking price $price kr")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    Snackbar.make(view, "Bid accepted", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}