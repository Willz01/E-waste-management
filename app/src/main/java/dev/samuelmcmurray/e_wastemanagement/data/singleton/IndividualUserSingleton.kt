package dev.samuelmcmurray.e_wastemanagement.data.singleton

import dev.samuelmcmurray.e_wastemanagement.data.model.IndividualUser

class IndividualUserSingleton private constructor(){

    var individualUser: IndividualUser?= null

    companion object {
        val getInstance = IndividualUserSingleton()
    }
}