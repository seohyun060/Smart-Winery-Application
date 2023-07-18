package com.example.smart_winery

import android.graphics.drawable.Drawable

data class WineInfo(private val mWine_location:Int, private val mWine_img: Drawable?) :Cloneable {
    public override fun clone(): WineInfo = super.clone() as WineInfo
    var Wine_location:Int? = mWine_location
    var Wine_Img:Drawable? = mWine_img
}