package com.yagmurali.emlak.activities

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.yagmurali.emlak.R
import com.yagmurali.emlak.database.DatabaseHandler
import com.yagmurali.emlak.databinding.ActivityAddNewMenkulBinding
import com.yagmurali.emlak.models.RealEstateModel
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddNewMenkulActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivityAddNewMenkulBinding? = null

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var saveImageToInternalStorage: Uri? = null
    private var latitude: Double = 0.0
    private var longtitude: Double = 0.0

    private var mRealEstateDetails: RealEstateModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewMenkulBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getRealEstateList()

        setSupportActionBar(binding?.toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarAddPlace?.setNavigationOnClickListener {
            onBackPressed()
        }

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            mRealEstateDetails = intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as RealEstateModel
        }
        dateSetListener = DatePickerDialog.OnDateSetListener {
                datePicker, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        if (mRealEstateDetails != null) {
            supportActionBar?.title = "Menkul değiştirme"
            binding?.etTitle?.setText(mRealEstateDetails!!.title)
            saveImageToInternalStorage = Uri.parse(mRealEstateDetails!!.image)
            binding?.etDescription?.setText(mRealEstateDetails!!.description)
            binding?.etDate?.setText(mRealEstateDetails!!.date)
            binding?.etLocation?.setText(mRealEstateDetails!!.location)
            latitude = mRealEstateDetails!!.latitude
            longtitude = mRealEstateDetails!!.longitude

            binding?.ivPlaceImage?.setImageURI(saveImageToInternalStorage)

            binding?.btnSave?.text = "Güncelle"
        }


        binding?.etDate?.setOnClickListener(this)
        binding?.btnSave?.setOnClickListener(this)
        binding?.tvAddImage?.setOnClickListener(this)


        //getSupportActionBar()?.hide()

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddNewMenkulActivity,
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH),
                ).show()
            }
            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems =
                    arrayOf("Select photo from gallery", "Capture photo from camera")
                pictureDialog.setItems(pictureDialogItems) { dialog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> takePhotoFromCamera()
                    }
                }
                pictureDialog.show()
            }
            R.id.btn_save -> {
                when {
                    binding?.etTitle?.text.isNullOrEmpty() -> {
                        Toast.makeText(this,
                        "Başlık alanını giriniz",
                        Toast.LENGTH_SHORT).show()
                    }
                    binding?.etDescription?.text.isNullOrEmpty() -> {
                        Toast.makeText(this,
                            "Açıklama alanını giriniz",
                            Toast.LENGTH_SHORT).show()

                    }
                    binding?.etDate?.text.isNullOrEmpty() -> {
                        Toast.makeText(this,
                            "Tarih alanını giriniz",
                            Toast.LENGTH_SHORT).show()

                    }
//                    binding?.etLocation?.text.isNullOrEmpty() -> {
//                        Toast.makeText(this,
//                            "Lokasyon alanını giriniz",
//                            Toast.LENGTH_SHORT).show()
//                    }
                else -> {
                        val data = RealEstateModel(
                            if (mRealEstateDetails == null) 0 else mRealEstateDetails!!.id,
                            binding?.etTitle?.text.toString(),
                            saveImageToInternalStorage.toString(),
                            binding?.etDescription?.text.toString(),
                            binding?.etDate?.text.toString(),
                            binding?.etLocation?.text.toString(),
                            latitude,
                            longtitude
                        )
                        val handler = DatabaseHandler(this)
                        if (mRealEstateDetails == null) {
                            val ok = handler.addRealEstate(data)
                            if (ok > 0) {
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                        } else {
                            val ok = handler.updateRealEstate(data)
                            if (ok > 0) {
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                        }
                    }
                }

            }
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                        saveImageToInternalStorage = saveImageToInternalStorage(selectedImageBitmap)

                        Log.e("Saved image : ", "Path:: ${saveImageToInternalStorage}")

                        binding?.ivPlaceImage?.setImageBitmap(selectedImageBitmap)
                    } catch (e : Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "exception",Toast.LENGTH_LONG).show()
                    }
                }
            } else if (requestCode == CAMERA) {
                val thumbnail : Bitmap = data!!.extras!!.get("data") as Bitmap
                binding?.ivPlaceImage?.setImageBitmap(thumbnail)
            }
        }
    }
    private fun takePhotoFromCamera() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
            .withListener(object:MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport )
                {
                    if (report.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(galleryIntent, CAMERA)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken)
                {

                }
            }).onSameThread().check();
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(this)
            .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object:MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport )
                {
                    if (report.areAllPermissionsGranted()) {
                        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, GALLERY)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>, token: PermissionToken)
                {

                }
            }).onSameThread().check();
    }
    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this).setMessage("İzin gerekli")
            .setPositiveButton("Ayarlara Git")
            {_, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("İptal") {
                dialog, _ ->
                dialog.dismiss()
            }
    }
    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding?.etDate?.setText(sdf.format(cal.time).toString())
    }

    private fun saveImageToInternalStorage(bitmap : Bitmap) : Uri {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

//        try {
//            val stream: OutputStream = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//            stream.flush()
//            stream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        FileOutputStream(file).use {
                stream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
        }

        return Uri.parse(file.absolutePath)
    }

    private fun getRealEstateList() {
        val handler = DatabaseHandler(this)
        val list: ArrayList<RealEstateModel> = handler.getRealEstateList()

        if (list.isNotEmpty()) {
            for (i in list) {
                Log.e("Title: ", i.toString())
            }
        }

    }

    companion object {
        private const val GALLERY = 1;
        private const val CAMERA = 2;
        private const val IMAGE_DIRECTORY = "EmlakResimler";

    }
}