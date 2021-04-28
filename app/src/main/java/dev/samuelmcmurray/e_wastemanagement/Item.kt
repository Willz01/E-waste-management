package dev.samuelmcmurray.e_wastemanagement

data class Item(var name: String, var userID: String, var id: String, var purchaseYear: String?, var model: String, var imageURI: String? = "", var description : String) {
}