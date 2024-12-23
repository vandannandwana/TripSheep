package com.vandan.tripsheep.navigations

sealed class Screens(val route:String){
    data object HomeScreen: Screens("home_screen")
    data object SearchScreen: Screens("search_screen")
    data object ProfileScreen: Screens("profile_screen")
    data object SplashScreen: Screens("splash_screen")
    data object GetStartedScreen: Screens("get_started_screen")
    data object LoginScreen: Screens("login_screen")
    data object SignUpScreen: Screens("signup_screen")
    data object TripPackageScreen: Screens("trip_package_screen")
    data object TripPlanScreen: Screens("trip_plan_screen")
    data object HotelScreen: Screens("hotel_screen")
}