package com.ikinciyenibayiagi.domain.useCases

import androidx.lifecycle.LiveData
import com.ikinciyenibayiagi.domain.entities.statics.StaticsResponseItem
import com.ikinciyenibayiagi.domain.repository.StaticsRepository
import com.ikinciyenibayiagi.domain.repository.post
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


interface StaticsUseCase {
    fun getContent(
        viewModelScope: CoroutineScope,
        content: LiveData<List<StaticsResponseItem>>,
        errorMessageSLE: LiveData<String>
    )
}

class StaticsUseCaseImpl @Inject constructor(var repository: StaticsRepository) : StaticsUseCase {

    override fun getContent(
        viewModelScope: CoroutineScope,
        content: LiveData<List<StaticsResponseItem>>,
        errorMessageSLE: LiveData<String>
    ) {
        repository.getContentData(viewModelScope, errorMessageSLE) { statics ->
            content.post(statics)
        }
    }

}


