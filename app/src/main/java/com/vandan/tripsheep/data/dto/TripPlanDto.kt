package com.vandan.tripsheep.data.dto

import com.vandan.tripsheep.data.local.TripPlan

data class TripPlanDto(
    val trip_id: String,
    val days: Int,
    val description: String,
    val itinerary: String,
    val name: String,
    val placesToVisit: List<String>,
    val priceAdult: Double,
    val priceChild: Double,
    val priceOffer: Double,
    val tripImages: List<String>
)

fun TripPlanDto.toTripPlan(): TripPlan {
    return TripPlan(
        trip_id = trip_id,
        days = days,
        description = description,
        itinerary = itinerary,
        name = name,
        placesToVisit = placesToVisit,
        priceAdult = priceAdult,
        priceChild = priceChild,
        priceOffer = priceOffer,
        tripImages = tripImages
    )
}