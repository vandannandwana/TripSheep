package com.vandan.tripsheep.presentations.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.vandan.tripsheep.R
import com.vandan.tripsheep.data.local.TemporaryUser
import com.vandan.tripsheep.data.local.User
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel = hiltViewModel<LoginViewModel>()
//    val signUpState = viewModel.signUpState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val signUpState = viewModel.userRegister.collectAsStateWithLifecycle().value

    if(signUpState.userRegisterSuccess) {
        navHostController.popBackStack()
        signUpState.userRegisterSuccess = false
        navHostController.navigate(Screens.HomeScreen.route)
    }

    var fullName by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var rePassword by rememberSaveable {
        mutableStateOf("")
    }

    var otp by rememberSaveable {
        mutableStateOf("")
    }

    val emailSubmitted = viewModel.emailSubmitted.collectAsStateWithLifecycle().value
    val isOtpVerified = viewModel.isOtpVerified.collectAsStateWithLifecycle().value



    Scaffold(modifier.background(MaterialTheme.colorScheme.background)) { ip ->
        BoxWithConstraints(modifier = Modifier.padding(ip)) {
            val height = maxHeight
            val width = maxWidth

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Sign Up",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = height / 64, top = height / 32)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(width / 24),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SignUpFacebookButton(modifier = Modifier) {}
                    SignUpGoogleButton(modifier = Modifier) {}

                }

                Text(text = "Or", color = Color.DarkGray.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(height / 64))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = width / 12),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTextField(value = email, label = "Email") {
                        email = it
                    }
                    if(emailSubmitted.emailSubmissionSuccess) {
                        Spacer(modifier = Modifier.height(height / 64))
                        CustomTextField(value = otp, label = "Full Name") {
                            otp = it
                        }
                    }

                    if(isOtpVerified.otpVerificationSuccess) {

                        Spacer(modifier = Modifier.height(height / 64))
                        CustomTextField(value = fullName, label = "Full Name") {
                            fullName = it
                        }
                        Spacer(modifier = Modifier.height(height / 64))
                        CustomTextField(value = password, label = "Password") {
                            password = it
                        }
                        Spacer(modifier = Modifier.height(height / 64))
                        CustomPasswordField(label = "Re-Enter Password", value = rePassword) {
                            rePassword = it
                        }
                    }

//                    if (signUpState.value.isLoading) {
//                        Spacer(modifier = Modifier.height(height / 64))
//                        CircularProgressIndicator()
//                    } else if (signUpState.value.loginError != null) {
//                        signUpState.value.loginError?.let {
//                            Spacer(modifier = Modifier.height(height / 64))
//                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//                        }
//                        signUpState.value.loginError = null
//                    }
//                    if (signUpState.value.signUpSuccess) {
//                        navHostController.popBackStack()
//                        signUpState.value.signUpSuccess = false
//                        navHostController.popBackStack()
//                        navHostController.navigate(Screens.HomeScreen.route)
//                    }
                    Spacer(modifier = Modifier.height(height / 64))

                    SignUpButton(modifier = Modifier, buttonText = if(!emailSubmitted.emailSubmissionSuccess)"Get Otp";else if (!isOtpVerified.otpVerificationSuccess) "Verify Otp"; else "Sign Up", textModifier = Modifier) {
                        if (email.isNotEmpty() && !emailSubmitted.emailSubmissionSuccess) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.submitEmail(TemporaryUser(email= email,otp=""))
                            }
                        }
                        else if(otp.isNotEmpty() && emailSubmitted.emailSubmissionSuccess && !isOtpVerified.otpVerificationSuccess) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.verifyOtp(TemporaryUser(email= email,otp=otp))
                            }
                        }else if(emailSubmitted.emailSubmissionSuccess && isOtpVerified.otpVerificationSuccess && fullName.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty() && (password == rePassword)){

                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.registerUser(User(_id = "", name = fullName, email = email, password = password))
                            }

                        }
                        else {
                            Toast.makeText(context, "Please fill all the required fields", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                }


            }

        }
    }

}

@Composable
fun SignUpButton(modifier: Modifier,buttonText:String = "signup", textModifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        elevation = ButtonDefaults.buttonElevation(24.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.mint_green).copy(alpha = 0.7f),
            contentColor = colorResource(id = R.color.black)
        )
    ) {
        Text(
            text = buttonText,
            modifier = textModifier,
            color = Color.DarkGray,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SignUpGoogleButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, modifier = modifier, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC84F4F)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_logo), modifier = Modifier.align(
                Alignment.CenterVertically
            ), contentDescription = "google_logo"
        )
        Text(text = "Google   ", modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun SignUpFacebookButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, modifier = modifier, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF039AE4)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook_logo),
            contentDescription = "facebook_logo"
        )
        Text(text = "Facebook")
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        label = { Text(text = label, color = Color.LightGray) },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = { onValueChange(it) })

}

@Composable
fun CustomPasswordField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    TextField(modifier = Modifier.fillMaxWidth(),
        value = value,
        label = { Text(text = label, color = Color.LightGray) },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = { onValueChange(it) })

}