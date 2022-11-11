package com.yagmurali.emlak.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yagmurali.emlak.R
import com.yagmurali.emlak.adapters.RealEstateAdapter
import com.yagmurali.emlak.database.DatabaseHandler
import com.yagmurali.emlak.databinding.ActivityMainBinding
import com.yagmurali.emlak.models.RealEstateModel
import com.yagmurali.emlak.utils.SwipeToEditCallback
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            getRealEstateListFromLocalDB()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        var btnAct: FloatingActionButton = findViewById(R.id.fabAddHappyPlace)
        getRealEstateListFromLocalDB()
        btnAct.setOnClickListener {
            val intent = Intent(this, AddNewMenkulActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun getRealEstateListFromLocalDB() {

        val dbHandler = DatabaseHandler(this)

        val list = dbHandler.getRealEstateList()

        if (list.size > 0) {
            binding?.rvRealEstateList?.visibility = View.VISIBLE
            binding?.tvNoRecordsAvailable?.visibility = View.GONE
            setupHappyPlacesRecyclerView(list)
        } else {
            binding?.rvRealEstateList?.visibility = View.GONE
            binding?.tvNoRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    private fun setupHappyPlacesRecyclerView(happyPlacesList: ArrayList<RealEstateModel>) {

        binding?.rvRealEstateList?.layoutManager = LinearLayoutManager(this)
        binding?.rvRealEstateList?.setHasFixedSize(true)

        val placesAdapter = RealEstateAdapter(this, happyPlacesList)
        binding?.rvRealEstateList?.adapter = placesAdapter

        placesAdapter.setOnClickListener(object: RealEstateAdapter.OnClickListener {
            override fun onClick(position: Int, model: RealEstateModel) {
                val intent = Intent(this@MainActivity, RealEstateDetailsActivity::class.java)
                intent.putExtra(EXTRA_PLACE_DETAILS, model)
                startActivity(intent)
            }
        })

        val editSwipeHandler = object: SwipeToEditCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (binding?.rvRealEstateList?.adapter as RealEstateAdapter).notifyEditItem(
                    this@MainActivity,
                    viewHolder.absoluteAdapterPosition,
                    ADD_PLACE_ACTIVITY_REQUEST_CODE
                )
            }
        }

        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(binding?.rvRealEstateList)
    }


    companion object {
        var ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PLACE_DETAILS = "extre place details"
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_PLACE_ACTIVITY_REQUEST_CODE) {
                getRealEstateListFromLocalDB()
            }
        }
    }

}