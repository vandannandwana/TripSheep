package com.vandan.tripsheep.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandan.tripsheep.data.dto.TripPlanDto
import com.vandan.tripsheep.data.dto.toTripPlan
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.TripPlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val tripService: TripService
):ViewModel() {

    init {
        Log.d("getTrips","init")
        viewModelScope.launch {
            getTrips()
        }
    }

    private val _trips = MutableStateFlow(TripPlanState())
    val trips = _trips.asStateFlow()

    private suspend fun _getTrips(): Flow<DataResource<List<TripPlanDto>>> = flow {
        emit(DataResource.Loading())
        val result = tripService.getTrips()
        Log.d("getTrips",result.toString())
        emit(DataResource.Success(result))
    }.flowOn(Dispatchers.IO)
        .catch {
            Log.d("getTrips",it.message.toString())
            emit(DataResource.Error(it.message))
        }

    private suspend fun getTrips(){

        _getTrips().onEach { trips ->
            when(trips){
                is DataResource.Error -> {
                    Log.d("getTrips",trips.message.toString())
                    _trips.value = TripPlanState().copy(error = trips.message)
                }
                is DataResource.Loading -> {
                    _trips.value = TripPlanState().copy(isLoading = true)
                }
                is DataResource.Success -> {
                    if (!trips.data.isNullOrEmpty()){
                        Log.d("getTrips",trips.data.toString())
                        _trips.value = TripPlanState().copy(trips = trips.data.map { it.toTripPlan() })
                    }
                }
            }
        }.collect()

    }

}