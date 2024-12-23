package com.vandan.tripsheep.presentations.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.vandan.tripsheep.data.local.LoginUserModel
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.UserState
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
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    @Named("userPreference")
    private val userPreferences: SharedPreferences,
    @Named("userDatabase")
    private val userDatabase: CollectionReference,
    private val tripService: TripService
) : ViewModel() {


    private val _user = MutableStateFlow(UserState())
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("Profile_Check", "Getting User")
            getUser(LoginUserModel("nandviky4@gmail.com", ""))
        }
    }

    private fun _getUser(user: LoginUserModel): Flow<DataResource<UserState>> = flow {
        emit(DataResource.Loading())
        val result = tripService.getUser(user)
        emit(DataResource.Success(UserState(user = result)))

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    private suspend fun getUser(user: LoginUserModel) {

        _getUser(user).onEach {
            when (it) {
                is DataResource.Error -> {
                    Log.e("ProfileViewModel", "Error fetching user: ${it.message}")
                    _user.value = UserState(error = it.message.toString())
                }

                is DataResource.Loading -> {
                    Log.d("ProfileViewModel", "Loading user...")
                    _user.value = UserState(isLoading = true)
                }

                is DataResource.Success -> {
                    if (it.data?.user != null) {
                        _user.value = UserState(isLoading = false, user = it.data.user)
                    }
                }
            }
        }.collect()

    }


//    private suspend fun _getUser(): Flow<DataResource<User>> = flow {
//        Log.d("Profile_Check","TTesting User")
//        val uid = auth.currentUser?.uid
//        emit(DataResource.Loading())
//        uid.let {
//            if(it != null){
//                Log.d("Profile_Check","User Fetched")
//                val result =userDatabase.document(it).get().await().toObject(User::class.java)
//                emit(DataResource.Success(result))
//                Log.d("Profile_Check","User Emitted")
//            }
//        }
//    }.flowOn(Dispatchers.IO)
//        .catch{
//            emit(DataResource.Error(it.message))
//        }
//
//    private suspend fun getUser(){
//        _getUser().collect{
//            Log.d("Profile_Check","Testing User")
//            when(it){
//                is DataResource.Error -> _user.value = UserState().copy(error = it.message)
//                is DataResource.Loading -> _user.value = UserState().copy(isLoading = true)
//                is DataResource.Success -> _user.value = UserState().copy(user = it.data)
//            }
//        }
//    }

    fun logoutUser() {
        userPreferences.edit().clear().apply()
    }

}