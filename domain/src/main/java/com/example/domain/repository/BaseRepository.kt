package com.example.domain.repository

import com.example.data.APIInterface
import com.example.data.NetworkHandler

open class BaseRepository(val api: APIInterface) {

    val networkHandler: NetworkHandler by lazy { NetworkHandler() }



//    fun <T> sendRequestSilent(
//        viewModelScope: CoroutineScope,
//        client: suspend () -> GenericResponse<T>,
//        onSuccess: ((T) -> Unit)? = null
//    ) {
//        val onFailed =  { _: String?, _: Int?, _: T? -> }
//        networkHandler.makeAPIRequest(client, onSuccess, onFailed, null)
//    }
//
//    fun <T> sendRequest(
//        viewModelScope: CoroutineScope,
//        client: suspend () -> GenericResponse<T>,
//        onSuccess: ((T) -> Unit),
//        onErrorAction: ((String?, Int?, T?) -> Unit)
//    ) {
//        makeAPIRequest(client, onSuccess, onErrorAction, loadingDetection)
//    }

}
