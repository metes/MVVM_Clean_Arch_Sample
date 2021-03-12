package com.ikinciyenibayiagi.domain.repository


import com.ikinciyenibayiagi.GenericResponse
import com.ikinciyenibayiagi.domain.entities.history.HistoryResponseItem
import com.ikinciyenibayiagi.domain.entities.statics.StaticsResponseItem
import retrofit2.http.GET
import retrofit2.http.Query


interface CovidEndpoints {

    @GET("/statistics")
    suspend fun getStatics(): GenericResponse<List<StaticsResponseItem>>

    @GET("/countries")
    suspend fun getCountries(): GenericResponse<List<String>>

    @GET("/history")
    suspend fun getHistory(
        @Query("country") country: String,
        @Query("day") day: String
    ): GenericResponse<List<HistoryResponseItem>>

}
