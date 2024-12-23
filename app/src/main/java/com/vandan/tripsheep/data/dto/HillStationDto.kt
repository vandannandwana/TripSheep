package com.vandan.tripsheep.data.dto

import com.vandan.tripsheep.data.local.HillStation

data class HillStationDto(
    val _id: String,
    val city: String,
    val hillId: String,
    val images: List<String>,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val rating: Double,
    val state: String
)


fun HillStationDto.toHillStation(): HillStation {
    return HillStation(
        _id = _id,
        city = city,
        hillId = hillId,
        images = images,
        latitude = latitude,
        longitude = longitude,
        name = name,
        rating = rating,
        state = state
    )
}