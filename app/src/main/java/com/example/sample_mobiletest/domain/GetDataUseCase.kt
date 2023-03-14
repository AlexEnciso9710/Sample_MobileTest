package com.example.sample_mobiletest.domain

import com.example.sample_mobiletest.utils.MapsActivity

class GetDataUseCase (private  val mapsRepository: MapsRepository) {

    fun getData(): com.example.sample_mobiletest.utils.Result<GeneralResults> {
        return mapsRepository.getRandomUser()
    }
}