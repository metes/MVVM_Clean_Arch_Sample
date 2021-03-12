package com.ikinciyenibayiagi.domain.entities.history

data class HistoryResponseItem(
    val cases: Cases,
    val continent: String,
    val country: String,
    val day: String,
    val deaths: Deaths,
    val population: Int,
    val tests: Tests,
    val time: String
)