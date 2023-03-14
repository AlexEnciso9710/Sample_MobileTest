package com.example.sample_mobiletest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MapViewModel (private val getDataUseCase: GetDataUseCase)  : ViewModel(){

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean>
    get() = _isRefresh

    fun update(isRefreshing: Boolean) {
        _isRefresh.value = isRefreshing
    }

    fun getData() {

        viewModelScope.launch {
            val  result= getDataUseCase.getRandomUser()
            when(result) {
                is com.example.sample_mobiletest.utils.Result.Success<*> -> {}
                is com.example.sample_mobiletest.utils.Result.Error -> {}
            }
        }
    }
}