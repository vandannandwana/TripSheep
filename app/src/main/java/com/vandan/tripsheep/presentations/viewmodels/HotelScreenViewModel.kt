package com.vandan.tripsheep.presentations.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vandan.tripsheep.data.dto.HotelDto
import com.vandan.tripsheep.data.dto.toHotel
import com.vandan.tripsheep.data.local.Hotel
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HotelScreenViewModel @Inject constructor(
    private val apiService: TripService
) : ViewModel() {

    val _hotelInfo = MutableStateFlow(DataState<Hotel>())
    val hotelInfo = _hotelInfo.asStateFlow()


    fun _getHotelInfo(hotelId: String) = flow {

        emit(DataResource.Loading())
        val result = apiService.getHotel(hotelId)
        emit(DataResource.Success(result.toHotel()))

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    suspend fun getHotelInfo(hotelId: String) {

        _getHotelInfo(hotelId).onEach {
            when (it) {
                is DataResource.Error -> _hotelInfo.value = _hotelInfo.value.copy(error = it.message.toString())
                is DataResource.Loading -> _hotelInfo.value = _hotelInfo.value.copy(isLoading = true)
                is DataResource.Success -> _hotelInfo.value = _hotelInfo.value.copy(data = it.data, isLoading = false)
            }
        }.collect()
    }


}