package com.vandan.tripsheep.presentations.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandan.tripsheep.data.dto.toHillStation
import com.vandan.tripsheep.data.dto.toTripPackage
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.HillStationsState
import com.vandan.tripsheep.data.resource.TripPackageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val tripService: TripService
) : ViewModel() {

    private val _tripPackageState = MutableStateFlow(TripPackageState())
    val tripPackageState = _tripPackageState.asStateFlow()

    private val _hillStationsState = MutableStateFlow(HillStationsState())
    val hillStationsState = _hillStationsState.asStateFlow()

    init {
        viewModelScope.launch {
            getTripPackages()
            getHillStations()
        }
    }

    private fun _getHillStations(): Flow<DataResource<HillStationsState>> = flow {
        emit(DataResource.Loading())
        val result = tripService.getHillStations()
        emit(DataResource.Success(HillStationsState().copy(hillStations = result.map { it.toHillStation() })))
    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    private suspend fun getHillStations() {

        _getHillStations().onEach {

            when (it) {
                is DataResource.Error -> _hillStationsState.value =
                    _hillStationsState.value.copy(error = it.message.toString())

                is DataResource.Loading -> _hillStationsState.value =
                    _hillStationsState.value.copy(isLoading = true)

                is DataResource.Success -> {
                    if(it.data?.hillStations != null){
                        if(it.data.hillStations.isNotEmpty()){
                            _hillStationsState.value = _hillStationsState.value.copy(
                                hillStations = it.data.hillStations,
                                isLoading = false
                            )
                        }
                    }

                }
            }

        }.collect()

    }


    private fun _getTripPackages(): Flow<DataResource<TripPackageState>> = flow {

        emit(DataResource.Loading())

        val result = tripService.getPackages()
        emit(DataResource.Success(TripPackageState().copy(tripPackages = result.map { it.toTripPackage() })))

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    private suspend fun getTripPackages() {
        _getTripPackages().onEach {
            when (it) {
                is DataResource.Error -> {
                    _tripPackageState.value = TripPackageState(error = it.message.toString())
                }

                is DataResource.Loading -> {
                    _tripPackageState.value = TripPackageState(isLoading = true)
                }

                is DataResource.Success -> {
                    if (it.data != null) {
                        _tripPackageState.value =
                            TripPackageState(tripPackages = it.data.tripPackages)
                    }
                }
            }
        }.collect()
    }

}