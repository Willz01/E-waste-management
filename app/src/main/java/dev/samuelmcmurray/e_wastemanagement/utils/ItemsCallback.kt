package dev.samuelmcmurray.e_wastemanagement.utils

import dev.samuelmcmurray.e_wastemanagement.data.model.Item


interface ItemsCallback {
    fun onCallback(value: ArrayList<Item>)
}