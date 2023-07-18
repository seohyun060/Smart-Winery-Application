package com.example.smart_winery

data class PairingInfo (
    private val id:String
    ,private val category: String
    ,private val imgsrc: String
    ,private val pairing_names:List<String>
) :Cloneable {
    public override fun clone(): PairingInfo = super.clone() as PairingInfo
    var Pairing_Id:String? = id
    var Pairing_category:String? = category
    var Pairing_image: String? = imgsrc
    var Pairing_names:List<String> = pairing_names
}