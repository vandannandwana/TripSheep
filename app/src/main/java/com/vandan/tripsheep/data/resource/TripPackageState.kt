package com.vandan.tripsheep.data.resource

import com.vandan.tripsheep.data.local.TripPackage

data class TripPackageState (
    val tripPackages: List<TripPackage>?= emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)