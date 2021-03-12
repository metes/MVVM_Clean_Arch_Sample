package com.example.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

open class BaseRepository @Inject constructor() {

//    @Inject
//    lateinit var networkHandler: NetworkHandler
//
//    @Inject
//    lateinit var api: APIInterface
//
//    val retrofitClient: APIInterface
//        get() {
//            return APIClient.retrofitBuilder
//                .client(APIClient.okHttpClient)
//                .build()
//                .create(APIInterface::class.java)
//        }
}

fun <T> LiveData<T>.post(value: T) {
    (this as MutableLiveData).postValue(value)
}