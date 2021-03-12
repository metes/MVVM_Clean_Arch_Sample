package com.example.domain.repository

import com.example.data.APIClient
import com.example.data.NetworkHandler
import com.example.domain.entities.statics.StaticsResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StaticsRepository @Inject constructor() {

    @Inject lateinit var apiClient: APIClient

    val retrofitClient: APIEndpoints
        get() {
            return apiClient.retrofitBuilder
                .client(apiClient.okHttpClient)
                .build()
                .create(APIEndpoints::class.java)
        }


    fun getContentData(
        viewModelScope: CoroutineScope,
        result: (ArrayList<StaticsResponseItem>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (false) {
                // todo add room implementation
            } else {
                NetworkHandler.makeNetworkRequest(
                    viewModelScope,
                    { retrofitClient.getStatics() },
                    { result(it) },
                    { message, code, response ->
                        result(response?.resultObject ?: arrayListOf())
                    }
                )
            }
        }
    }



}

