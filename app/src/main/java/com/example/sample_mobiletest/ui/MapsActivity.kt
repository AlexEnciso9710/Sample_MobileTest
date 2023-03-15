package com.example.sample_mobiletest.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sample_mobiletest.R
import com.example.sample_mobiletest.ui.model.InfoUser
import com.example.sample_mobiletest.utils.MapsViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var nameEditText: EditText
    private lateinit var lastnameEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var stateEditText: EditText
    private lateinit var zipcodeEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var image: ImageView

    private lateinit var mapsViewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapsViewModel = ViewModelProvider(this, MapsViewModelFactory())[MapsViewModel::class.java]

        bindView()
        initMapFragment()
        initRefreshLayout()
        initObservers()

    }

    private fun bindView() {
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
    }

    private fun initMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.Map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun initRefreshLayout() = swipeRefreshLayout.run {
        setOnRefreshListener {
            getUser()
        }
    }

    private fun initObservers() {
        mapsViewModel.mapsUiModel.observe(this) {
            swipeRefreshLayout.isRefreshing = it.showRefresh
            if (it.infoUser != null) getInfoUserSuccess(it.infoUser)
            if (it.exception != null) getInfoUserError(it.exception)
        }
    }

    private fun getUser() = mapsViewModel.getUser()

    private fun getInfoUserSuccess(infoUser: InfoUser) = infoUser.run {
        nameEditText.setText(name)
    }

    private fun getInfoUserError(exception: Exception) = Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }
}
