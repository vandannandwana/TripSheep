package com.vandan.tripsheep.data.local

data class Hotel(
    val _id:String,
    val ac: Boolean,
    val address: String,
    val discountedPrice: String,
    val hotelName: String,
    val imageUrls: List<String>,
    val parking: Boolean,
    val person: Int,
    val restaurent: Boolean,
    val roomPrice: String,
    val stars: Int,
    val wifi: Boolean
)