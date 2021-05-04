package dev.samuelmcmurray.e_wastemanagement.data.singleton

import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser

class CompanyUserSingleton private constructor(){

    var companyUser: CompanyUser?= null

    companion object {
        val getInstance = CompanyUserSingleton()
    }
}