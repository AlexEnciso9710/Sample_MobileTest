package com.example.sample_mobiletest.ui.model

data class MapsUiModel(val showRefresh: Boolean,
                       val infoUser: InfoUser?,
                       val exception: Exception?)

data class InfoUser(val name: String)
