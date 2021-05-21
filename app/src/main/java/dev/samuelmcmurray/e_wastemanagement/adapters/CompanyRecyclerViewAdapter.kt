package dev.samuelmcmurray.e_wastemanagement.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser
import dev.samuelmcmurray.e_wastemanagement.ui.company.CompanyFragment


class CompanyRecyclerViewAdapter(
    private var context: Context, private var companies: List<CompanyUser>,
    private var view: View
): RecyclerView.Adapter<CompanyRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(companyView: View): RecyclerView.ViewHolder(companyView) {
        var image: ImageView = companyView.findViewById(R.id.company_img)
        var companyName: TextView = companyView.findViewById(R.id.company_name)
        var location: TextView = companyView.findViewById(R.id.location)
        var hasRecycling: TextView = companyView.findViewById(R.id.recycling)
        var ratingBar: RatingBar = companyView.findViewById(R.id.rating_card)
        var cardViewCompany: CardView = companyView.findViewById(R.id.company_card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater  = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.company_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.companyName.text = companies[position].companyName
        val imageUri = Uri.parse(companies[position].imageURL)
        holder.location.text = companies[position].city
        if (companies[position].hasRecycling) {
            holder.hasRecycling.text = "We Recycle"
        } else {
            holder.hasRecycling.text = "We do not Recycle"
        }
        holder.ratingBar.numStars = companies[position].rating.toInt()
        Glide.with(context).load(imageUri).into(holder.image)

        holder.cardViewCompany.setOnClickListener {
            view ->
            val company = companies[position]
            val appCompatActivity: AppCompatActivity = view.context as AppCompatActivity
            val companyFragment = CompanyFragment(company)
            appCompatActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.shops_fragment, companyFragment).addToBackStack(null).commit()

        }
    }

    override fun getItemCount(): Int {
        return companies.size
    }
}