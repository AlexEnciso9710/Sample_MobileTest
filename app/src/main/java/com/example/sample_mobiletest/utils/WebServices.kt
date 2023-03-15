package com.example.sample_mobiletest.utils

import com.example.sample_mobiletest.domain.model.ResponseApi
import com.example.sample_mobiletest.domain.model.UserInfo
import com.example.sample_mobiletest.ui.model.InfoUser
import retrofit2.http.GET

interface WebServices {

    @GET("/api/")
    suspend fun getRandomUser(): ResponseApi
}