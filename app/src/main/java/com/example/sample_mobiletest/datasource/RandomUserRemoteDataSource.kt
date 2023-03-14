package com.example.sample_mobiletest.datasource

import com.example.sample_mobiletest.domain.model.ResponseApi
import com.example.sample_mobiletest.domain.model.UserInfo
import com.example.sample_mobiletest.utils.Result.Error
import com.example.sample_mobiletest.utils.Result.Success
import com.example.sample_mobiletest.utils.WebServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RandomUserRemoteDataSource {

    fun getRandomUser(): com.example.sample_mobiletest.utils.Result<UserInfo> {
        var userInfo: com.example.sample_mobiletest.utils.Result<UserInfo>? = null
        try {
            val retrofit = Retrofit.Builder().baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create()).build()
            val webServices: WebServices = retrofit.create(WebServices::class.java)

            webServices.getRandomUser().enqueue(object : Callback<ResponseApi> {
                override fun onResponse(call: Call<ResponseApi>, response: Response<ResponseApi>) {
                    userInfo = if (response.isSuccessful) Success(response.body()?.userInfo?.get(0)!!)
                    else Error(Exception(response.message()))
                }

                override fun onFailure(call: Call<ResponseApi>, throwable: Throwable) {
                    userInfo = Error(Exception(throwable.message))
                }
            })
        } catch (exception: Exception) {
            userInfo = Error(Exception(exception.message))
        }

        return userInfo as com.example.sample_mobiletest.utils.Result<UserInfo>
    }
}
