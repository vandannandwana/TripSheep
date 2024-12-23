package com.vandan.tripsheep.data.dto

import com.vandan.tripsheep.data.local.Hotel

data class HotelDto(
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

fun HotelDto.toHotel(): Hotel {
    return Hotel(
        _id = _id,
        ac = ac,
        address = address,
        discountedPrice = discountedPrice,
        hotelName = hotelName,
        imageUrls = imageUrls,
        parking = parking,
        person = person,
        restaurent = restaurent,
        roomPrice = roomPrice,
        stars = stars,
        wifi = wifi
    )
}