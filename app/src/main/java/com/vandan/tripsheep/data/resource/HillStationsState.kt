package com.vandan.tripsheep.data.resource

import com.vandan.tripsheep.data.local.HillStation

data class HillStationsState(
    val isLoading: Boolean = false,
    val hillStations: List<HillStation> = emptyList(),
    val error: String = ""
)