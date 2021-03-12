package com.example

data class GenericResponse<T>(
    val errors: List<String>?,
    val get: String,
    val parameters: List<String>?,
    val response: T,
    val results: Int?,
) {

    val isSuccess: Boolean get() {
        return response != null
    }

    val message: String get() {
      return when (errors.isNullOrEmpty()) {
          true  -> ""
          false -> errors[0]
      }
    }

}