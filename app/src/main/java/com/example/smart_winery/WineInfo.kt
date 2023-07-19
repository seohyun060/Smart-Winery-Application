package com.example.smart_winery

class WineInfo(     private val row:Int
                    ,private val col:Int
                    ,private val id:String
                    ,private val eng_name:String
                    ,private val wine_img: String
                    ,private val price: Int
                    ,private val sweet: Int
                    ,private val acid: Int
                    ,private val body: Int
                    ,private val tannin: Int
                    ,private val aromas: MutableList<AromaInfo>
                    ,private val alcohol: String
                    ,private val temp:Int
                    ,private val type:String
                    ,private val pairings:MutableList<PairingInfo>
) :Cloneable {
    public override fun clone(): WineInfo = super.clone() as WineInfo
    var Wine_Floor:Int = row
    var Wine_Location:Int? = col
    var Wine_Id:String? = id
    var Wine_Name:String? = eng_name
    var Wine_Image:String? = wine_img
    var Wine_Price:Int?=price
    var Wine_Sweet:Int?=sweet
    var Wine_Acid:Int?=acid
    var Wine_Body:Int?=body
    var Wine_Tannin:Int?=tannin
    var Wine_Aromas:MutableList<AromaInfo>? = aromas
    var Wine_Alcohol:String? = alcohol
    var Wine_Temp:Int?=temp
    var Wine_Type:String = type
    var Wine_Pairings:MutableList<PairingInfo>? = pairings
    var Wine_Floor_Type = 0

    fun setWineFloorType(){
        if (Wine_Type == "Red" || Wine_Type == "Fortified"){
            Wine_Floor_Type = 1
        }
        else if (Wine_Type=="White" || Wine_Type == "Rose"){
            Wine_Floor_Type = 2
        }
        else {
            Wine_Floor_Type =3
        }
    }
    init {
        setWineFloorType()
    }


}

