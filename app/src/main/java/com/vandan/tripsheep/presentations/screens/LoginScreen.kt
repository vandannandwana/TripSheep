package com.vandan.tripsheep.presentations.screens

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
import com.vandan.tripsheep.data.local.LoginUserModel
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val loginState = viewModel.userRegister.collectAsStateWithLifecycle().value
    var email by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var password by rememberSaveable {
        mutableStateOf("")
    }

    if(loginState.userRegisterSuccess) {
        navHostController.popBackStack()
        loginState.userRegisterSuccess = false
        navHostController.navigate(Screens.HomeScreen.route)
    }


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
                    text = "Log in",
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

                    FacebookButton(modifier = Modifier) {}
                    GoogleButton(modifier = Modifier) {}

                }

                Text(text = "Or", color = Color.DarkGray.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.height(height / 64))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = width / 12),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        label = { Text(text = "Email", color = Color.LightGray) },
                        shape = CircleShape,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.white),
                            unfocusedContainerColor = colorResource(id = R.color.white),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = { email = it }
                    )
                    Spacer(modifier = Modifier.height(height / 64))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        label = { Text(text = "Password", color = Color.LightGray) },
                        shape = CircleShape,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorResource(id = R.color.white),
                            unfocusedContainerColor = colorResource(id = R.color.white),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = { password = it }
                    )

                    Spacer(modifier = Modifier.height(height / 64))

                    Text(
                        text = "Forgot password?",
                        color = colorResource(id = R.color.cool_gray)
                    )


//                    if (loginState.value.isLoading) {
//                        Spacer(modifier = Modifier.height(height / 64))
//                        CircularProgressIndicator()
//                    } else if (loginState.value.loginError != null) {
//                        loginState.value.loginError?.let {
//                            Spacer(modifier = Modifier.height(height / 64))
//                            Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
//                        }
//                        loginState.value.loginError = null
//                    }
//                    if (loginState.value.loginSuccess) {
//                        navHostController.popBackStack()
//                        loginState.value.loginSuccess = false
//                        navHostController.navigate(Screens.HomeScreen.route)
//                    }
                    Spacer(modifier = Modifier.height(height / 64))
                    LoginButton(modifier = Modifier, textModifier = Modifier) {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.loginUser(user = LoginUserModel(email, password), context = context)
                        }
                    }
                    Spacer(modifier = Modifier.height(height / 64))

                    Text(
                        text = "Register Yourself!",
                        color = colorResource(id = R.color.warm_yellow)
                    )

                }


            }

        }
    }

}

@Composable
fun LoginButton(modifier: Modifier, textModifier: Modifier, onClick: () -> Unit) {
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
            text = "Login",
            modifier = textModifier,
            color = Color.DarkGray,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun GoogleButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color(0xFFC84F4F)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            modifier = Modifier.align(Alignment.CenterVertically),
            contentDescription = "google_logo"
        )
        Text(text = "Google   ", modifier = Modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun FacebookButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults
            .buttonColors(
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