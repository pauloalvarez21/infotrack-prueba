package com.gaelectronica.infotrack_prueba

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast

class ThirdActivity : AppCompatActivity() {

    var btnFoto : Button? = null
    var cekTerminos : CheckBox? = null

    val REQUEST_CODE_TAKE_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        btnFoto = findViewById<CheckBox>(R.id.btnFoto)
        cekTerminos = findViewById(R.id.cebTratamiento)

        botonListener()
    }

    private fun botonListener() {
        btnFoto!!.setOnClickListener {

            if(cekTerminos!!.isChecked){
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Tiene que aceptar el tratamiento de datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
            takePicture.resolveActivity(packageManager)?.also {
                startActivityForResult(takePicture, REQUEST_CODE_TAKE_PHOTO)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            data?.extras?.let { bundle -> {
                val imageBitmap = bundle.get("data") as Bitmap
            } }

            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}