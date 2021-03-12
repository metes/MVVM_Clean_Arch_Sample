package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.data.APIClient
import com.example.domain.NetworkHandler.makeNetworkRequest
import com.example.domain.entities.statics.StaticsResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface StaticsRepository {
    fun getContentData(
        viewModelScope: CoroutineScope,
        errorMessageSLE: LiveData<String>,
        result: (List<StaticsResponseItem>) -> Unit
    )
}

@Singleton
class StaticsRepositoryImpl @Inject constructor(apiClient: APIClient): StaticsRepository {

     private val covidApi: CovidEndpoints = apiClient.api.create(CovidEndpoints::class.java)

    override fun getContentData(
        viewModelScope: CoroutineScope,
        errorMessageSLE: LiveData<String>,
        result: (List<StaticsResponseItem>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (false) {
                // todo add room implementation
            } else {
                makeNetworkRequest(
                    viewModelScope,
                    { covidApi.getStatics() },
                    { result(it.toMutableList()) },
                    { message, code, response -> errorMessageSLE.post(message) }
                )
            }
        }
    }



}

