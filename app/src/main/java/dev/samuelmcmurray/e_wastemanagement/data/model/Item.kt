package dev.samuelmcmurray.e_wastemanagement.data.model

data class Item(
    var name: String,
    var userID: String,
    var id: String,
    var type : String,
    var purchaseYear: String?,
    var image1: String?, var image2: String?, var image3: String?, var image4: String?,
    var model: String,
    var description: String
) {
}