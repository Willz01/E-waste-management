package dev.samuelmcmurray.e_wastemanagement.data.model

class CompanyUser(
    var uid: String,
    var companyName: String,
    var about: String,
    var userName: String,
    var storeID: String,
    var email: String,
    var address: String,
    var phoneNumber: String,
    var country: String,
    var city: String,
    var hasRecycling: Boolean,
    var hasImage: Boolean,
    var takesMobiles: Boolean,
    var takesComponents: Boolean,
    var takesOther: Boolean,
    var websiteURL: String
) {
    private var rating: Double = 0.0
    private var imageURL: String = ""
}