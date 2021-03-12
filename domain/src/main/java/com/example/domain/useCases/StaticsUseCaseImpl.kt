package com.example.domain.useCases

import androidx.lifecycle.LiveData
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.repository.StaticsRepository
import com.example.domain.repository.post
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


