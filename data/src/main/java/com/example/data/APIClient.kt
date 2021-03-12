package com.example.data

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


const val CONNECTION_TIMEOUT_SEC = 10L
const val BASE_URL: String = "https://covid-193.p.rapidapi.com"


//@InstallIn(ApplicationComponent::class)
//@Module
@Singleton
class APIClient @Inject constructor() {

    //private val sharedPref by inject<SharedPrefHelper>()

    private val accessToken: String? get() = ""//sharedPref.loadUser().accessToken

    private val stethoInterceptor: StethoInterceptor? by lazy {
        if (BuildConfig.DEBUG) StethoInterceptor() else null
    }

    val retrofitBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    private val okHttpClientBuilder by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
        stethoInterceptor?.let { builder.addNetworkInterceptor(it) }
        builder
    }

    val okHttpClient: OkHttpClient by lazy {
            okHttpClientBuilder.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("x-rapidapi-key", "BuildConfig.api_key")
                builder.addHeader("x-rapidapi-host", "BuildConfig.api_host")
                if (!accessToken.isNullOrBlank()) {
                    builder.addHeader("authorization", "Bearer $accessToken")
                }
                Log.d(javaClass.simpleName,"Access token Bearer $accessToken")
                chain.proceed(builder.build())
            }
            okHttpClientBuilder.build()
        }



}



