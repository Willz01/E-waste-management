package dev.samuelmcmurray.e_wastemanagement.data.model

class CompanyUser(
    var uid: String,
    var companyName: String,
    var userName: String,
    var storeID: String,
    var email: String,
    var phoneNumber: String,
    var country: String,
    var city: String,
    var hasImage: Boolean
) {

}