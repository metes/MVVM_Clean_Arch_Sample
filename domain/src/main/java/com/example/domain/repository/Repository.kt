package com.example.domain.repository

import com.example.data.APIInterface
import com.example.domain.entities.statics.StaticsResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(apii: APIInterface) : BaseRepository(apii) {


    fun getContentData(
        viewModelScope: CoroutineScope,
        result: (ArrayList<StaticsResponseItem>) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (false) {
                // todo add room implementation
            } else {
                networkHandler.makeNetworkRequest(
                    viewModelScope,
                    { api.getStatics() },
                    { result(it) },
                    { message, code, response ->
                        result(response?.resultObject ?: arrayListOf())
                    }
                )
            }
        }
    }


}

