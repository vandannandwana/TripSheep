package com.vandan.tripsheep.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vandan.tripsheep.presentations.screens.LoginScreen
import com.vandan.tripsheep.presentations.screens.SignUpScreen
import com.vandan.tripsheep.presentations.screens.MainScreen
import com.vandan.tripsheep.presentations.screens.TripPackageScreen
import com.vandan.tripsheep.presentations.screens.ProfileScreen
import com.vandan.tripsheep.presentations.screens.GetStartedScreen
import com.vandan.tripsheep.presentations.screens.HotelScreen
import com.vandan.tripsheep.presentations.screens.SearchScreen
import com.vandan.tripsheep.presentations.screens.TripPlanScreen
import com.vandan.tripsheep.presentations.screens.SplashScreen

@Composable
fun Navigation(navHostController: NavHostController, token: String?){

   NavHost(navController = navHostController, startDestination = if(token==null) Screens.SplashScreen.route else Screens.HomeScreen.route) {

      composable(Screens.HomeScreen.route){
         MainScreen(uid = token, navHostController = navHostController)
      }

      composable(Screens.SearchScreen.route){
         SearchScreen(navHostController = navHostController)
      }

      composable(Screens.ProfileScreen.route){
         ProfileScreen(navHostController=navHostController)
      }
       composable(Screens.SplashScreen.route) {
           SplashScreen(navHostController)
       }

       composable(Screens.LoginScreen.route) {
           LoginScreen(navHostController = navHostController)
       }

       composable(Screens.SignUpScreen.route) {
           SignUpScreen(navHostController=navHostController)
       }

       composable(Screens.GetStartedScreen.route) {
           GetStartedScreen(navHostController=navHostController)
       }

       composable(Screens.TripPackageScreen.route+"/{tripPackageId}") {
           val tripPackageId = it.arguments?.getString("tripPackageId")
           if(tripPackageId!=null) {
               TripPackageScreen(tripPackageId = tripPackageId,navhostController = navHostController)
           }
       }

       composable(Screens.TripPlanScreen.route+"/{tripPlanId}") {

           val tripPlanId = it.arguments?.getString("tripPlanId")
           if(tripPlanId !=null){

               TripPlanScreen(tripPlanId = tripPlanId, navHostController = navHostController)

           }

       }

       composable(Screens.HotelScreen.route+"/{hotelId}"){
           val hotelId = it.arguments?.getString("hotelId")
           if(hotelId != null){
               HotelScreen(hotelId = hotelId,navHostController = navHostController)
           }
       }


   }

}