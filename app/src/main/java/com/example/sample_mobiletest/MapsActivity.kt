package com.example.sample_mobiletest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var layoutMap: GoogleMap

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var nameEditText : EditText
    private lateinit var lastnameEditText : EditText
    private lateinit var streetEditText : EditText
    private lateinit var numberEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var stateEditText: EditText
    private lateinit var zipcodeEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var image: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.name)
        lastnameEditText = findViewById(R.id.LastName)
        streetEditText = findViewById(R.id.Street)
        numberEditText = findViewById(R.id.ETNumber)
        countryEditText = findViewById(R.id.ETCountry)
        stateEditText = findViewById(R.id.ETState)
        zipcodeEditText = findViewById(R.id.ETZipCode)
        ageEditText = findViewById(R.id.ETAge)
        genderEditText = findViewById(R.id.ETGender)
        emailEditText = findViewById(R.id.ETemail)
        phoneEditText = findViewById(R.id.ETPhone)
        image = findViewById(R.id.imageMainView)

        swipeRefreshLayout = findViewById(R.id.swiperefresh)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.Map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        swipeRefreshLayout.setOnRefreshListener {
            getData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        layoutMap = googleMap

        val sidney = LatLng(-31.5, 151.00)

        layoutMap.addMarker(MarkerOptions()
                .position(sidney)
                .title(""))
        layoutMap.moveCamera(CameraUpdateFactory.newLatLng(sidney))

        getData()
    }


    fun getData() {

        val retrofit = Retrofit.Builder().baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create()).build()

        val apiService = retrofit.create(WebServices::class.java)

        apiService.getrandomuser().enqueue(object : Callback<GeneralResults>{
            override fun onResponse(call: Call<GeneralResults>, response: Response<GeneralResults>) {
                 Toast.makeText(this@MapsActivity, "OK", Toast.LENGTH_SHORT).show()

                nameEditText.setText(response.body()?.results?.get(0)?.genname?.name)
                lastnameEditText.setText(response.body()?.results?.get(0)?.genname?.lastname)
                streetEditText.setText(response.body()?.results?.get(0)?.location?.street?.street)
                numberEditText.setText(response.body()?.results?.get(0)?.location?.street?.houseNumber.toString())
                countryEditText.setText(response.body()?.results?.get(0)?.location?.country)
                stateEditText.setText(response.body()?.results?.get(0)?.location?.state)
                zipcodeEditText.setText(response.body()?.results?.get(0)?.location?.zipcode.toString())
                ageEditText.setText(response.body()?.results?.get(0)?.age?.age.toString())
                genderEditText.setText(response.body()?.results?.get(0)?.gender)
                emailEditText.setText(response.body()?.results?.get(0)?.email)
                phoneEditText.setText(response.body()?.results?.get(0)?.cell)

             //Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);
                Glide.with(this@MapsActivity).load(response.body()?.results?.get(0)?.image1?.image).into(image)

                newMarker(
                        city = response.body()?.results?.get(0)?.location?.city.orEmpty(),
                        longitude = response.body()?.results?.get(0)?.location?.cordinates?.longitude.orEmpty().toDouble(),
                        latitude = response.body()?.results?.get(0)?.location?.cordinates?.latitude.orEmpty().toDouble())

            }

            override fun onFailure(call: Call<GeneralResults>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "Mamaste", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun newMarker (city: String, longitude: Double, latitude: Double)= layoutMap.run {
        layoutMap.clear()
        val newlocation = LatLng(latitude, longitude)

        layoutMap.addMarker(MarkerOptions()
                .position(newlocation)
                .title(city))
        layoutMap.moveCamera(CameraUpdateFactory.newLatLng(newlocation))
    }

    interface WebServices{
        @GET("/api/")
        fun getrandomuser(): Call<GeneralResults>
    }

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


    data class GeneralLocation (@SerializedName ("street") val street: Street,
                                @SerializedName("city") val city: String,
                                @SerializedName("state") val state: String,
                                @SerializedName("country") val country: String,
                                @SerializedName("postcode") val zipcode: Int,
                                @SerializedName("coordinates")val cordinates: Coordinates,
                                )

    data class Coordinates(@SerializedName ("latitude") val latitude: String,
                           @SerializedName ("longitude") val longitude: String)

    data class Street (@SerializedName ("number") val houseNumber: Int,
                       @SerializedName ("name") val street: String)

    data class Dob (@SerializedName("age") val age: Int)

    data class Pic (@SerializedName ("medium") val image: String)


}
