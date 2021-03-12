package com.example.domain.repository

import com.example.data.APIClient
import com.example.data.NetworkHandler
import com.example.domain.entities.statics.StaticsResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface StaticsRepository {
    fun getContentData(
        viewModelScope: CoroutineScope,
        result: (ArrayList<StaticsResponseItem>) -> Unit
    )
}

@Singleton
class StaticsRepositoryImpl @Inject constructor(var apiClient: APIClient): StaticsRepository {

     val apiEndpoints = apiClient.api.create(APIEndpoints::class.java)


    override fun getContentData(
        viewModelScope: CoroutineScope,
        result: (ArrayList<StaticsResponseItem>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (false) {
                // todo add room implementation
            } else {
                NetworkHandler.makeNetworkRequest(
                    viewModelScope,
                    { apiEndpoints.getStatics() },
                    { result(it) },
                    { message, code, response ->
                        result(response?.resultObject ?: arrayListOf())
                    }
                )
            }
        }
    }



}

