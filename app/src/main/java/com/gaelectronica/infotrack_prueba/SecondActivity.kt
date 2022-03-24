package com.gaelectronica.infotrack_prueba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SecondActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var txtUserName : TextView?= null
    var txtPhone : TextView? = null
    var txtWebside : TextView? = null
    var txtNameCom : TextView? = null
    var txtcatchPhrase : TextView? = null
    var txtBs : TextView? = null

    var stringLink = "https://jsonplaceholder.typicode.com/users"

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

        volleyRequest = Volley.newRequestQueue(this)

        getUser(stringLink, name.toString())
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
}