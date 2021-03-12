package com.ikinciyenibayiagi.data

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


const val CONNECTION_TIMEOUT_SEC = 10L
const val BASE_URL: String = "https://covid-193.p.rapidapi.com"

interface APIClient {
    val api: Retrofit
}

@Module
@InstallIn(ApplicationComponent::class)
class APIClientImpl @Inject constructor(): APIClient{

    //private val sharedPref by inject<SharedPrefHelper>()

    private val accessToken: String? get() = "" + System.currentTimeMillis() //sharedPref.loadUser().accessToken

    @get:Provides
    @Singleton
    val stethoInterceptor: StethoInterceptor? by lazy {
        if (BuildConfig.DEBUG) StethoInterceptor() else null

    }

    @Singleton
    @get:Provides
    override val api: Retrofit get() = getRetrofitBuilder().client(okHttpClient).build()

    @Singleton
    @Provides
    fun getRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    }

    @get:Provides
    @Singleton
    val okHttpClientBuilder by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
        stethoInterceptor?.let { builder.addNetworkInterceptor(it) }
        builder
    }

    @get:Provides
    @Singleton
    val okHttpClient: OkHttpClient by lazy {
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



}



