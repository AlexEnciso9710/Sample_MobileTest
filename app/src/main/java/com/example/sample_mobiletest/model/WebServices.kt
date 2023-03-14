package com.example.sample_mobiletest.model

import com.example.sample_mobiletest.utils.MapsActivity
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("/api/")
    fun getrandomuser(): Call<GeneralResults>

}