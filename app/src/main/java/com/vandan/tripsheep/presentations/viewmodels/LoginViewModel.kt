package com.vandan.tripsheep.presentations.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.vandan.tripsheep.data.local.LoginUserModel
import com.vandan.tripsheep.data.local.TemporaryUser
import com.vandan.tripsheep.data.local.User
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.data.resource.DataResource
import com.vandan.tripsheep.data.resource.EmailSubmissionState
import com.vandan.tripsheep.data.resource.OtpVerificationState
import com.vandan.tripsheep.data.resource.UserRegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
//    @ApplicationContext private val context: Context,
//    private val auth: FirebaseAuth,
//    @Named("userDatabase")
//    private val userDatabase: CollectionReference,
    @Named("userPreference")
    private val userPreferences: SharedPreferences,
    private val tripService: TripService
) : ViewModel() {
//    private val _loginState = MutableStateFlow(LoginState())
//    val loginState = _loginState.asStateFlow()
//
//    private val _signUpState = MutableStateFlow(SignUpState())
//    val signUpState = _signUpState.asStateFlow()

    private val _emailSubmitted = MutableStateFlow(EmailSubmissionState())
    val emailSubmitted = _emailSubmitted.asStateFlow()

    private val _isOtpVerified = MutableStateFlow(OtpVerificationState())
    val isOtpVerified = _isOtpVerified.asStateFlow()

    private val _userRegister = MutableStateFlow(UserRegisterState())
    val userRegister = _userRegister.asStateFlow()




    fun _submitEmail(temporaryUser: TemporaryUser):Flow<DataResource<EmailSubmissionState>> = flow {

        emit(DataResource.Loading())
        val result = tripService.sendOtp(temporaryUser = temporaryUser)
        Log.d("Email Submit",result.message)
        emit(DataResource.Success(EmailSubmissionState(emailSubmissionSuccess = true, isLoading = false)))

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }

    suspend fun submitEmail(temporaryUser: TemporaryUser) {

        _submitEmail(temporaryUser).onEach {
            when(it){
                is DataResource.Error -> _emailSubmitted.value = EmailSubmissionState(emailSubmissionError = it.message)
                is DataResource.Loading -> _emailSubmitted.value = EmailSubmissionState(isLoading = true)
                is DataResource.Success -> _emailSubmitted.value = EmailSubmissionState(emailSubmissionSuccess = true, isLoading = false)
            }
        }.collect()

    }


    fun _verifyOtp(temporaryUser: TemporaryUser): Flow<DataResource<OtpVerificationState>> = flow {

        emit(DataResource.Loading())

        val result = tripService.verifyOtp(temporaryUser = temporaryUser)
        Log.d("Otp Verify",result.message)
        result.let {
            if(result.message == "OTP Verify Successful") {
                emit(DataResource.Success(OtpVerificationState(otpVerificationSuccess = true, isLoading = false)))
            }
        }

    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }



    suspend fun verifyOtp(temporaryUser: TemporaryUser){

        _verifyOtp(temporaryUser).onEach {
            when(it){
                is DataResource.Error -> _isOtpVerified.value = OtpVerificationState(otpVerificationError = it.message)
                is DataResource.Loading -> _isOtpVerified.value = OtpVerificationState(isLoading = true)
                is DataResource.Success -> _isOtpVerified.value = OtpVerificationState(otpVerificationSuccess = true, isLoading = false)
            }
        }.collect()

    }

    fun _registerUser(user: User): Flow<DataResource<UserRegisterState>> = flow {
        emit(DataResource.Loading())
        val result = tripService.registerUser(user = user)
        Log.d("User Register",result.message)
        result.let {
            if(result.message == "User Registered Successfully") {
                emit(DataResource.Success(UserRegisterState(userRegisterSuccess = true, isLoading = false)))
            }
        }
    }.catch {
        emit(DataResource.Error(it.message.toString()))
    }


    suspend fun registerUser(user: User) {

        _registerUser(user).onEach {
            when(it){
                is DataResource.Error -> _userRegister.value = UserRegisterState(userRegisterError = it.message)
                is DataResource.Loading -> _userRegister.value = UserRegisterState(isLoading = true)
                is DataResource.Success -> _userRegister.value = UserRegisterState(userRegisterSuccess = true, isLoading = false)
            }
        }.collect()

    }

    fun _loginUser(user: LoginUserModel,context: Context): Flow<DataResource<UserRegisterState>> = flow {
        emit(DataResource.Loading())
        val result = tripService.loginUser(user)
        Log.d("User Login",result.token)
        result.let {
            if(result.token != "") {
                userPreferences.edit().putString("token", result.token).apply()
                emit(DataResource.Success(UserRegisterState(userRegisterSuccess = true, isLoading = false)))
            }
        }
    }.catch {
        emit(DataResource.Error(it.message.toString()))
        Log.d("User Login",it.message.toString())
    }


    suspend fun loginUser(user: LoginUserModel,context: Context) {

        _loginUser(user,context).onEach {
            when(it){
                is DataResource.Error -> _userRegister.value = UserRegisterState(userRegisterError = it.message)
                is DataResource.Loading -> _userRegister.value = UserRegisterState(isLoading = true)
                is DataResource.Success -> _userRegister.value = UserRegisterState(userRegisterSuccess = true, isLoading = false)
            }
        }.collect()

    }









//    fun loginUser(email: String, password: String) {
//        _loginState.value = LoginState().copy(isLoading = true)
//        Log.d("Login_Check", "Loading")
//        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
//            _loginState.value = LoginState().copy(isLoading = false, loginSuccess = true)
//            auth.currentUser?.uid.let {
//                userPreferences.edit().putString("uid", it).apply()
//            }
//
//            Log.d("Login_Check", "Success")
//        }.addOnFailureListener {
//            _loginState.value = LoginState().copy(isLoading = false, loginError = it.message)
//            Log.d("Login_Check", "Failure")
//        }
//    }



//    fun signUpUser(name: String, longitude: Double = 0.0, latitude: Double = 0.0, email: String,password: String) {
//        _signUpState.value = SignUpState().copy(isLoading = true)
//        Log.d("SignUp_Check", "Loading")
//        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
//
//            auth.currentUser?.uid.let {
//                createUserProfile(
//                    name = name,
//                    email = email,
//                    password = password,
//                    longitude = longitude,
//                    latitude = latitude,
//                    uid = it
//                )
//            }
//            Log.d("SignUp_Check", "Success")
//        }.addOnFailureListener {
//            _signUpState.value = SignUpState().copy(isLoading = false, loginError = it.message)
//            Log.d("SignUp_Check", "Failure")
//        }
//
//    }

//    private fun createUserProfile(name: String, email: String, password: String, longitude: Double, latitude: Double, uid: String?) {
//        val user = hashMapOf(
//            "name" to name,
//            "email" to email,
//            "password" to password,
//            "longitude" to longitude,
//            "latitude" to latitude
//        )
//        uid.let { uid ->
//            if(uid != null) {
//                userDatabase.document(uid).set(user).addOnSuccessListener {
//                    _signUpState.value = SignUpState().copy(isLoading = false, signUpSuccess = true)
//                    Log.d("SignUp_Register_Check", "Success")
//                    userPreferences.edit().putString("uid", uid).apply()
//                }.addOnFailureListener {
//                    _signUpState.value = SignUpState().copy(isLoading = false, loginError = it.message)
//                    Log.d("SignUp_Register_Check", it.message.toString())
//                }
//            }
//        }
//
//    }




}