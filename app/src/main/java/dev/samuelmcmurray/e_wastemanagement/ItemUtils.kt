package dev.samuelmcmurray.e_wastemanagement

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "ItemUtils"

class ItemUtils {

    companion object {
        fun newInstance() = ItemUtils()
    }

    fun addItemToFirebase(item: Item): Boolean {
        val itemCollection = FirebaseFirestore.getInstance().collection("items")
        val itemMap = hashMapOf(
            "name" to item.name,
            "UID" to item.userID,
            "purchaseYear" to item.purchaseYear,
            "id" to item.id,
            "model" to item.model,
            "image" to item.imageURI!!
        )

        var success = false
        itemCollection.add(itemMap).addOnSuccessListener {
            Log.d(TAG, "onSuccess: ${item.id} added!")
            success = true
        }.addOnFailureListener {
            Log.d(TAG, "onFailure: ${item.id} failed to be added!")
            success = false
        }

        return success
    }

    fun readItemsFromFirebase(itemsCallback: ItemsCallback): Boolean {
        val itemCollection = FirebaseFirestore.getInstance().collection("items")
        var success = false
        itemCollection.get().addOnSuccessListener { items ->
            if (items.isEmpty) {
                Log.d(TAG, "readItemsFromFirebase: Returned empty from firebase")
                success = false
            } else {
                val itemsList = ArrayList<Item>()
                for (item in items) {

                    Log.d(TAG, "readItemsFromFirebase: ${item.id} => ${item.data}")

                    val name = item.data["name"]
                    val UID = item.data["UID"]
                    val purchaseYear = item.data["purchaseYear"]
                    val id = item.data["id"]
                    val model = item.data["model"]!!
                    val imageURI = item.data["image"]

                    itemsList.add(
                        Item(
                            name.toString(),
                            UID.toString(),
                            id.toString(),
                            purchaseYear.toString(),
                            model.toString(), imageURI.toString()
                        )
                    )

                }
                // used interface to hold list, because this method only returns the success boolean condition
                itemsCallback.onCallback(itemsList)
                success = true
            }

        }
        return success
    }


}