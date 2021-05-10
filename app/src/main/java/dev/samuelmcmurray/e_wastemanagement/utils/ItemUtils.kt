package dev.samuelmcmurray.e_wastemanagement.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dev.samuelmcmurray.e_wastemanagement.data.model.Item

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
            "type" to item.type,
            "purchaseYear" to item.purchaseYear,
            "image1" to item.image1,
            "image2" to item.image2,
            "image3" to item.image3,
            "image4" to item.image4,
            "id" to item.id,
            "model" to item.model,
            "description" to item.description
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
                    val image1 = item.data["image1"]
                    val image2 = item.data["image2"]
                    val image3 = item.data["image3"]
                    val image4 = item.data["image4"]
                    val id = item.data["id"]
                    val type = item.data["type"]
                    val model = item.data["model"]!!
                    val description = item.data["description"]

                    itemsList.add(
                        Item(
                            name.toString(),
                            UID.toString(),
                            id.toString(), type.toString(),
                            purchaseYear.toString(), image1 as String?,
                            image2 as String?, image3 as String?, image4 as String?,
                            model.toString(), description as String
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

    // TODO : Method for filtering below by item type

    /**
     * type - "Phone/Laptop" or "Computer part" or "Other"
     */
    fun filterOutType(itemsCallback: ItemsCallback, type: String): Boolean {
        val itemCollection =
            FirebaseFirestore.getInstance().collection("items").whereEqualTo("type", type)
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
                    val image1 = item.data["image1"]
                    val image2 = item.data["image2"]
                    val image3 = item.data["image3"]
                    val image4 = item.data["image4"]
                    val id = item.data["id"]
                    val type = item.data["type"]
                    val model = item.data["model"]!!
                    val description = item.data["description"]

                    itemsList.add(
                        Item(
                            name.toString(),
                            UID.toString(),
                            id.toString(), type.toString(),
                            purchaseYear.toString(), image1 as String?,
                            image2 as String?, image3 as String?, image4 as String?,
                            model.toString(), description as String
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