package com.vandan.tripsheep.data.local

data class HillStation(
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
