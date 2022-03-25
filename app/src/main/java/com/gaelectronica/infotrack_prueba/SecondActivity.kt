package com.gaelectronica.infotrack_prueba

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import java.util.jar.Manifest

class SecondActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var txtUserName : TextView?= null
    var txtPhone : TextView? = null
    var txtWebside : TextView? = null
    var txtNameCom : TextView? = null
    var txtcatchPhrase : TextView? = null
    var txtBs : TextView? = null
    var btnTomarFoto : Button? = null

    var stringLink = "https://jsonplaceholder.typicode.com/users"

    val REQUEST_CODE_TAKE_PHOTO = 1
    private val PERMISO_CAMARA: Int = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var extras = intent.extras
        var name = extras!!.get("name")

        txtUserName = findViewById<TextView>(R.id.txtUserName)
        txtPhone = findViewById<TextView>(R.id.txtPhone)
        txtWebside = findViewById<TextView>(R.id.txtWebsite)
        txtNameCom = findViewById<TextView>(R.id.txtNameCom)
        txtcatchPhrase = findViewById<TextView>(R.id.txtcatchPhrase)
        txtBs = findViewById<TextView>(R.id.txtBs)
        btnTomarFoto = findViewById<Button>(R.id.btnTomarFoto)

        botonListener()

        volleyRequest = Volley.newRequestQueue(this)

        getUser(stringLink, name.toString())

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun botonListener() {
        btnTomarFoto!!.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(this@SecondActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                    dispatchTakePictureIntent()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISO_CAMARA)
                }
            }

        }
    }

    private fun getUser(url: String, name: String) {
        val jsonUser = JsonArrayRequest (
            Request.Method.GET, url,
            {
                              response: JSONArray ->
                try {
                    for(i in 0..response.length() -1) {
                        var bNombre = response.getJSONObject(i).getString("name")

                        if (bNombre.equals("$name")) {
                            txtUserName!!.text ="User Name: " + response.getJSONObject(i).getString("username")
                            txtPhone!!.text = "Phone: " + response.getJSONObject(i).getString("phone")
                            txtWebside!!.text = "Webside: " + response.getJSONObject(i).getString("website")

                            var company = response.getJSONObject(i).getJSONObject("company")

                            txtNameCom!!.text = "Name: " + company.getString("name")
                            txtcatchPhrase!!.text = "CatchPhrase: " + company.getString("catchPhrase")
                            txtBs!!.text = "Bs: " + company.optString("bs")

                        }
                    }
                }catch (e: JSONException) {
                    e.printStackTrace()
                }

            },
            {
                    error: VolleyError? ->
                try {
                    Log.d("Error", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }
        )

        volleyRequest!!.add(jsonUser)
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

            startActivity(Intent(this, ThirdActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISO_CAMARA -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    dispatchTakePictureIntent()
                }
            }
            else -> {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}