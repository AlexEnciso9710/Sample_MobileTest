package com.example.sample_mobiletest.model

import com.example.sample_mobiletest.utils.MapsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

      fun getRandomUser (): com.example.sample_mobiletest.utils.Result<GeneralResults> {

            var result: Result<GeneralResults> = null

            try {
                  val retrofit = Retrofit.Builder().baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create()).build()
                  val apiServices: WebServices = retrofit.create(WebServices::class.java)

                  apiServices.getrandomuser().enqueue(object : Callback<GeneralResults> {
                        override fun onResponse(call: Call<GeneralResults>, response: Response<GeneralResults>) {
                              if (response.isSuccessful) result = Result.success(response.body()!!)
                              result = Result.failure(Throwable(response.message()))
                        }

                        override fun onFailure(call: Call<GeneralResults>, t: Throwable) {
                              result = Result.failure(t)
                        }
                  })
            } catch (exception: Exception){
                  result = Result.failure(Throwable(exception.message))
            }

            return result as com.example.sample_mobiletest.utils.Result<GeneralResults>
      }

}