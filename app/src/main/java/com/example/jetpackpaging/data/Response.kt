package com.example.jetpackpaging.data

sealed class Response<out T> {

    data class Success<R>(val errorCode: Int, val data: R, val errorMsg: String) : Response<R>()
    data class Failure(val errorMsg: String) : Response<Nothing>()
}