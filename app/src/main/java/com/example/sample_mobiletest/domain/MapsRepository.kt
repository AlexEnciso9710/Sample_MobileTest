package com.example.sample_mobiletest.domain

import com.example.sample_mobiletest.model.RemoteDataSource

class MapsRepository(private val remoteDataSource: RemoteDataSource) {

    fun getRandomUser() = remoteDataSource.getRandomUser()

}