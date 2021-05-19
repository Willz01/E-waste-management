package dev.samuelmcmurray.e_wastemanagement.utils

import dev.samuelmcmurray.e_wastemanagement.data.model.CompanyUser

interface CompaniesCallBack {
    fun onCallback(value: ArrayList<CompanyUser>)
}