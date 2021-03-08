package com.example.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope

class StaticsUseCase {

    val content: LiveData<ArrayList<StaticsResponseItem>> = MutableLiveData()
    val repository: Repository by lazy { Repository() }

    fun getContent(viewModelScope: CoroutineScope) {
        repository.getContentData(viewModelScope) { statics ->
            content.post(statics)
        }
    }

}



fun <T>LiveData<T>.post(value: T) {
    (this as MutableLiveData).postValue(value)
}