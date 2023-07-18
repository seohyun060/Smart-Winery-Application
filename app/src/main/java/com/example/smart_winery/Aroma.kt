package com.example.smart_winery

data class AromaInfo (
                  private val id:String
                  ,private val category: String
                  ,private val imgsrc: String
                 ,private val aroma_names:List<String>
) :Cloneable {
    public override fun clone(): AromaInfo = super.clone() as AromaInfo
    var Aroma_Id:String? = id
    var Aroma_category:String? = category
    var Aroma_image: String? = imgsrc
    var Aroma_names:List<String>? = aroma_names
}