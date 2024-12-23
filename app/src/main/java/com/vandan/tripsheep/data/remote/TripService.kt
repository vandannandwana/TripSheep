package com.vandan.tripsheep.data.remote

import com.vandan.tripsheep.data.dto.HillStationDto
import com.vandan.tripsheep.data.dto.HotelDto
import com.vandan.tripsheep.data.dto.MessageResponseDto
import com.vandan.tripsheep.data.dto.TripPackageDto
import com.vandan.tripsheep.data.dto.TripPlanDto
import com.vandan.tripsheep.data.local.HillStation
import com.vandan.tripsheep.data.local.LoginUserModel
import com.vandan.tripsheep.data.local.TemporaryUser
import com.vandan.tripsheep.data.local.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//192.168.29.233

//tomtom map = VvAdLEsSxY41YQfxCNpsPhWWnFnUZGoN
interface TripService {
    @GET("trips")
    suspend fun getTrips():List<TripPlanDto>
    @GET("getPackages")
    suspend fun getPackages():List<TripPackageDto>

    @GET("hills")
    suspend fun getHillStations():List<HillStationDto>

    @POST("loginuser")
    suspend fun loginUser(@Body user:LoginUserModel):JWT_TOKEN

    @POST("otpsend")
    suspend fun sendOtp(@Body temporaryUser: TemporaryUser):MessageResponseDto

    @POST("verifyotp")
    suspend fun verifyOtp(@Body temporaryUser: TemporaryUser):MessageResponseDto

    @POST("registeruser")
    suspend fun registerUser(@Body user: User):MessageResponseDto

    @POST("getUser")
    suspend fun getUser(@Body user: LoginUserModel):User

    @GET("hotel/{hotelId}")
    suspend fun getHotel(@Path("hotelId") hotelId:String):HotelDto


}