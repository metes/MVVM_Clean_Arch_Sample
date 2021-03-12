package com.example.data

import androidx.lifecycle.MutableLiveData
import com.example.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//@InstallIn(ApplicationComponent::class)
//@Module
object NetworkHandler  {

    private val generalErrorMessage = "unknown error" // todo

    enum class RequestState {
        Loading, Invisible, Error
    }


    fun <T> makeNetworkRequest(
        coroutineScope: CoroutineScope,
        client: suspend () -> GenericResponse<T>,
        onSuccess: (T) -> Unit,
        onError: (httpErrorMessage: String?, httpErrorCode: Int?, resultObject: GenericResponse<T>?) -> Unit,
        loadingDetection: MutableLiveData<RequestState>? = null
    ) {
        coroutineScope.launch {
            loadingDetection?.postValue(RequestState.Loading)
            try {
                val result: Result<GenericResponse<T>> = withContext(Dispatchers.IO) {
                    Result.runCatching { (client.invoke()) }
                }
                startActionByResult(result.getOrNull(), onSuccess, onError)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                onError(generalErrorMessage, -1, null)
            }
            loadingDetection?.postValue(RequestState.Invisible)
        }
    }

    private fun <T> startActionByResult(
        result: GenericResponse<T>?,
        onSuccess: (T) -> Unit,
        onErrorAction: (httpErrorMessage: String?, httpErrorCode: Int?, resultObject: GenericResponse<T>?) -> Unit
    ) {
        when (result?.isSuccess) {
            true -> onSuccess(result.resultObject)
            false -> onErrorAction(result.message, result.results, result)
            else -> onErrorAction(generalErrorMessage, -1, null)
        }
    }

}