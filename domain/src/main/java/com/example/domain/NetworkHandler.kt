package com.example.domain

import androidx.lifecycle.MutableLiveData
import com.example.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object NetworkHandler  {

    enum class RequestState {
        Loading, Invisible, Error
    }

    fun <T> makeNetworkRequest(
        scope: CoroutineScope,
        client: suspend () -> GenericResponse<T>,
        onSuccess: (T) -> Unit,
        onError: (httpErrorMessage: String, httpErrorCode: Int, resultObject: GenericResponse<T>?) -> Unit,
        loadingDetection: MutableLiveData<RequestState>? = null
    ) {
        scope.launch {
            loadingDetection?.postValue(RequestState.Loading)
            try {
                val result: Result<GenericResponse<T>> = withContext(Dispatchers.IO) {
                    Result.runCatching { (client.invoke()) }
                }
                startActionByResult(result.getOrNull(), onSuccess, onError)
                loadingDetection?.postValue(RequestState.Invisible)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                onError("", -1, null)
                loadingDetection?.postValue(RequestState.Error)
            }
        }
    }

    private fun <T> startActionByResult(
        result: GenericResponse<T>?,
        onSuccess: (T) -> Unit,
        onErrorAction: (httpErrorMessage: String, httpErrorCode: Int, resultObject: GenericResponse<T>?) -> Unit
    ) {
        when (result?.isSuccess) {
            true -> onSuccess(result.response)
            false -> onErrorAction(result.message, result.results?: -1, result)
            else -> onErrorAction("", -1, null)
        }
    }

}