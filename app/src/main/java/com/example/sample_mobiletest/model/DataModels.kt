package com.example.sample_mobiletest.model

import com.google.gson.annotations.SerializedName

data class GeneralResults (@SerializedName("results") val results: List<Result>)

data class  Result (@SerializedName("name") val genname: Name,
                    @SerializedName("gender") val gender: String,
                    @SerializedName("location") val location: GeneralLocation,
                    @SerializedName("dob") val age: Dob,
                    @SerializedName("email") val email: String,
                    @SerializedName("cell") val cell: String,
                    @SerializedName("picture") val image1: Pic)

data class Name (@SerializedName("title") val title: String,
                 @SerializedName("first") val name: String,
                 @SerializedName("last") val lastname: String)


data class GeneralLocation (@SerializedName("street") val street: Street,
                            @SerializedName("city") val city: String,
                            @SerializedName("state") val state: String,
                            @SerializedName("country") val country: String,
                            @SerializedName("postcode") val zipcode: Int,
                            @SerializedName("coordinates")val cordinates: Coordinates,
)

data class Coordinates(@SerializedName("latitude") val latitude: String,
                       @SerializedName("longitude") val longitude: String)

data class Street (@SerializedName("number") val houseNumber: Int,
                   @SerializedName("name") val street: String)

data class Dob (@SerializedName("age") val age: Int)

data class Pic (@SerializedName("medium") val image: String)