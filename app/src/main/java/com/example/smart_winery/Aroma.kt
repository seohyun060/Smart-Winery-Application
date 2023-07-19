package com.example.smart_winery

data class AromaInfo (
                  private var id:String
                  ,private var category: String
                  ,private var imgsrc: String
                 ,private var aroma_names:MutableList<String>
) :Cloneable {
    public override fun clone(): AromaInfo = super.clone() as AromaInfo
    var Aroma_Id:String? = id
    var Aroma_category:String? = category
    var Aroma_image: String? = imgsrc
    var Aroma_names:MutableList<String>? = aroma_names


}