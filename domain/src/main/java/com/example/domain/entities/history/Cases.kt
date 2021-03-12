package com.example.domain.entities.history

data class Cases(
    val `1M_pop`: String,
    val active: Int,
    val critical: Int,
    val new: String,
    val recovered: Int,
    val total: Int
)