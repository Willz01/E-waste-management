package dev.samuelmcmurray.e_wastemanagement.data.model

class IndividualUser(
    var uid: String,
    var firstName: String,
    var lastName: String,
    var userName: String,
    var email: String,
    var dob: String,
    var country: String,
    var city: String,
    var hasImage: Boolean
) {

}