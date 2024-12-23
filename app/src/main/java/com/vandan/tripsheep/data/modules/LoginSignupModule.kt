package com.vandan.tripsheep.data.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.vandan.tripsheep.data.remote.TripService
import com.vandan.tripsheep.utils.Constants
import com.vandan.tripsheep.utils.Constants.Companion.BASE_URL
import com.vandan.tripsheep.utils.Constants.Companion.USER_PREFERENCE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginSignupModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        val auth = FirebaseAuth.getInstance()
        return auth
    }

    @Provides
    @Singleton
    @Named("userDatabase")
    fun provideUserDatabase(): CollectionReference {
        return Firebase.firestore.collection("users")
    }


    @Named("userPreference")
    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): SharedPreferences {
        val userPreference = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        return userPreference
    }

    @Provides
    @Singleton
    fun provideTripService(): TripService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(TripService::class.java)
    }


}