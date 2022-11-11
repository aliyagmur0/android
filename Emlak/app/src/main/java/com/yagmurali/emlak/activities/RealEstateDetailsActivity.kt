package com.yagmurali.emlak.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yagmurali.emlak.R
import com.yagmurali.emlak.databinding.ActivityMainBinding
import com.yagmurali.emlak.databinding.ActivityRealEstateDetailsBinding
import com.yagmurali.emlak.models.RealEstateModel

class RealEstateDetailsActivity : AppCompatActivity() {

    private var binding: ActivityRealEstateDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealEstateDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var detailModel : RealEstateModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            detailModel = intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as RealEstateModel
            // detailModel = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)!! as RealEstateModel

        }

        if (detailModel != null) {
            setSupportActionBar(binding?.toolbarAddPlace)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = detailModel.title

            binding?.toolbarAddPlace?.setNavigationOnClickListener {
                onBackPressed()
            }
            binding?.ivPlaceImage?.setImageURI(Uri.parse(detailModel.image))
            binding?.tvDescription?.text = detailModel.description
            binding?.tvLocation?.text = detailModel.location
        }
    }
}