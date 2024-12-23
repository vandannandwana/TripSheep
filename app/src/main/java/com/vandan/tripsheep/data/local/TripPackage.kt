package com.vandan.tripsheep.data.local

data class TripPackage (
    val tripPackageId:String,
    val about: String,
    val hotels: List<Hotel>,
    val imageUrls: List<String>,
    val packageName: String
){
    constructor():this("0","Loading...", emptyList(), emptyList(),"Loading...")
}