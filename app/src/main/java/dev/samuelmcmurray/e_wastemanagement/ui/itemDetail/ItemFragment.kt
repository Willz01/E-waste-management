package dev.samuelmcmurray.e_wastemanagement.ui.itemDetail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.samuelmcmurray.e_wastemanagement.R
import dev.samuelmcmurray.e_wastemanagement.data.model.Item
import dev.samuelmcmurray.e_wastemanagement.data.singleton.CompanyUserSingleton
import dev.samuelmcmurray.e_wastemanagement.data.singleton.IndividualUserSingleton
import dev.samuelmcmurray.e_wastemanagement.utils.ItemUtils
import dev.samuelmcmurray.e_wastemanagement.utils.notification.NotificationHandler

private const val TAG = "ItemFragment"

class ItemFragment(var item: Item) : Fragment() {

    private lateinit var itemName: TextView
    private lateinit var itemModel: TextView
    private lateinit var itemDescription: TextView
    private lateinit var itemType: TextView

    private lateinit var itemImage1: ImageView
    private lateinit var itemImage2: ImageView
    private lateinit var itemImage3: ImageView
    private lateinit var itemImage4: ImageView

    private lateinit var bidButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_item, container, false)
        itemName = view.findViewById(R.id.item_name)
        itemDescription = view.findViewById(R.id.item_description)
        itemType = view.findViewById(R.id.item_type)
        itemModel = view.findViewById(R.id.item_model)

        itemImage1 = view.findViewById(R.id.image1)
        itemImage2 = view.findViewById(R.id.image2)
        itemImage3 = view.findViewById(R.id.image3)
        itemImage4 = view.findViewById(R.id.image4)

        bidButton = view.findViewById(R.id.bid_button)

        itemName.text = item.name
        itemModel.text = item.model
        itemType.text = item.type
        itemDescription.text = item.description

        if (IndividualUserSingleton.getInstance.individualUser?.userName != null) {
            Log.d(TAG, "onCreateView: Individual user logged in")
            bidButton.visibility = View.GONE
        }

        bidButton.setOnClickListener {
            val priceDialog = Dialog(requireContext())
            priceDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            priceDialog.setContentView(R.layout.price_dialog)

            val priceTextView = priceDialog.findViewById<TextView>(R.id.seeker_display)
            val priceSeekBar = priceDialog.findViewById<SeekBar>(R.id.appCompatSeekBar)
            val button = priceDialog.findViewById<Button>(R.id.button_price)

            priceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    priceTextView.text = progress.toDouble().toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // TODO("Not yet implemented")
                }

            })

            priceDialog.show()
            button.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Confirm bid price ${priceTextView.text} kr")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->

                        ItemUtils.newInstance().addBid(
                            item,
                            CompanyUserSingleton.getInstance.companyUser?.companyName.toString(),
                            priceTextView.text.toString().toDouble().toInt()
                        )
                        val notificationHandler: NotificationHandler =
                            NotificationHandler(
                                requireContext(),
                                itemName.text.toString(),
                                CompanyUserSingleton.getInstance.companyUser?.companyName.toString()
                            )
                        notificationHandler.callNotificationForCompanyUser()

                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()

            }
        }


        // images
        try {
            Glide.with(requireContext()).load(Uri.parse(item.image1)).into(itemImage1)
        } catch (e: Exception) {
            itemImage1.visibility = View.GONE
        }

        try {
            Glide.with(requireContext()).load(Uri.parse(item.image2)).into(itemImage2)
        } catch (e: Exception) {
            itemImage2.visibility = View.GONE
        }

        try {
            Glide.with(requireContext()).load(Uri.parse(item.image3)).into(itemImage3)
        } catch (e: Exception) {
            itemImage3.visibility = View.GONE
        }

        try {
            Glide.with(requireContext()).load(Uri.parse(item.image4)).into(itemImage4)
        } catch (e: Exception) {
            itemImage4.visibility = View.GONE
        }

        return view
    }

}