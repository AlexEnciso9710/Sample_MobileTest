package com.example.sample_mobiletest.utils

sealed class Result<out T: Any> {

    data class Success<out T : Any>(val data: T)

    data class Error (val exeption: Exception) :

    override fun toString(): String {
        return when (this) {
            is Success <*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

}