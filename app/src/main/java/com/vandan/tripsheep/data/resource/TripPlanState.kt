package com.vandan.tripsheep.data.resource

import com.vandan.tripsheep.data.local.TripPlan

data class TripPlanState(
    val isLoading:Boolean = false,
    val trips:List<TripPlan> = emptyList(),
    val error:String? = null
)
