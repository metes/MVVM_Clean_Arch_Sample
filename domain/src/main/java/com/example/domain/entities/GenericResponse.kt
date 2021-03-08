package com.example.domain.entities

data class GenericResponse<T>(
    val errors: List<String>,
    val results: Int,
    val resultObject: T
) {

    val isSuccess: Boolean get() {
        return resultObject != null
    }

    val message: String get() {
      return when (errors.isNullOrEmpty()) {
          true  -> ""
          false -> errors[0]
      }
    }

}