package com.example.domain.useCases

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.domain.entities.statics.StaticsResponseItem
import com.example.domain.repository.StaticsRepository
import com.example.domain.repository.post
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


interface StaticsUseCase {
    fun getContent(
        viewModelScope: CoroutineScope,
        content: LiveData<ArrayList<StaticsResponseItem>>
    )
}

class StaticsUseCaseImpl @Inject constructor(var repository: StaticsRepository): StaticsUseCase {

//    @Inject lateinit

    override fun getContent(viewModelScope: CoroutineScope, content: LiveData<ArrayList<StaticsResponseItem>>) {
        Log.d("StaticsUseCase", "StaticsUseCase çalıştı *************")
        repository.getContentData(viewModelScope) { statics ->
            content.post(statics)
        }
    }

}


