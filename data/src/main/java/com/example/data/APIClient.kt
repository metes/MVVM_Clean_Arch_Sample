package com.example.data

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val CONNECTION_TIMEOUT_SEC = 10L
const val BASE_URL: String = "https://covid-193.p.rapidapi.com"

object APIClient {

    //private val sharedPref by inject<SharedPrefHelper>()

    private val accessToken: String? get() = ""//sharedPref.loadUser().accessToken

    private val stethoInterceptor: StethoInterceptor? by lazy {
        if (BuildConfig.DEBUG) StethoInterceptor() else null
    }

    private val retrofitBuilder by lazy {
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

    private val okHttpClient: OkHttpClient by lazy {
            okHttpClientBuilder.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/json")
                builder.addHeader("x-rapidapi-key", BuildConfig.api_key)
                builder.addHeader("x-rapidapi-host", BuildConfig.api_host)
                if (!accessToken.isNullOrBlank()) {
                    builder.addHeader("authorization", "Bearer $accessToken")
                }
                Log.d(javaClass.simpleName,"Access token Bearer $accessToken")
                chain.proceed(builder.build())
            }
            okHttpClientBuilder.build()
        }

    val retrofitClient: APIInterface
        get() {
            return retrofitBuilder
                .client(okHttpClient)
                .build()
                .create(APIInterface::class.java)
        }

}



