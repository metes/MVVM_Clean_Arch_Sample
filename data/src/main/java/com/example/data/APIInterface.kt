package com.example.data

import com.example.domain.networkModel.GenericResponse
import com.example.domain.networkModel.history.HistoryResponseItem
import com.example.domain.networkModel.statics.StaticsResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("/statistics")
    suspend fun getStatics(): GenericResponse<ArrayList<StaticsResponseItem>>

    @GET("/countries")
    suspend fun getCountries(): GenericResponse<ArrayList<String>>

    @GET("/history")
    suspend fun getHistory(
        @Query("country") country: String,
        @Query("day") day: String
    ): GenericResponse<ArrayList<HistoryResponseItem>>

}
