package dev.samuelmcmurray.e_wastemanagement.data.singleton

class ItemImageSingletonHolder private constructor() {


    companion object{
        var getInstance = ItemImageSingletonHolder()
    }

    var image1Uri : String? = null
    var image2Uri : String? = null
    var image3Uri : String? = null
    var image4Uri : String? = null

}