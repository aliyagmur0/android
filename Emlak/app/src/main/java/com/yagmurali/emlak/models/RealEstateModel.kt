package com.yagmurali.emlak.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class RealEstateModel (
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val date: String,
    val location: String,
    val latitude : Double,
    val longitude: Double
)  : Serializable
// : Parcelable {
// constructor(parcel: Parcel) : this(
// parcel.readInt(),
// parcel.readString().toString(),
// parcel.readString().toString(),
// parcel.readString().toString(),
// parcel.readString().toString(),
// parcel.readString().toString(),
// parcel.readDouble(),
// parcel.readDouble()
// ) {
// }
//
// override fun writeToParcel(parcel: Parcel, flags: Int) {
// parcel.writeInt(id)
// parcel.writeString(title)
// parcel.writeString(image)
// parcel.writeString(description)
// parcel.writeString(date)
// parcel.writeString(location)
// parcel.writeDouble(latitude)
// parcel.writeDouble(longitude)
// }
//
// override fun describeContents(): Int {
// return 0
// }
//
// companion object CREATOR : Parcelable.Creator<RealEstateModel> {
// override fun createFromParcel(parcel: Parcel): RealEstateModel {
// return RealEstateModel(parcel)
// }
//
// override fun newArray(size: Int): Array<RealEstateModel?> {
// return arrayOfNulls(size)
// }
// }
// }