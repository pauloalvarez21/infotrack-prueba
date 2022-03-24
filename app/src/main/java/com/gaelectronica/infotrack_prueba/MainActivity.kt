package com.gaelectronica.infotrack_prueba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.gaelectronica.infotrack_prueba.adapter.UserListAdapter
import com.gaelectronica.infotrack_prueba.model.User
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var arrayList: ArrayList<User>? = null
    var userAdater: UserListAdapter? = null
    var layoutManayer: RecyclerView.LayoutManager? = null
    var reciclerUser: RecyclerView? = null

    var stringLink = "https://jsonplaceholder.typicode.com/users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reciclerUser = findViewById<RecyclerView>(R.id.rvUser)
        arrayList = ArrayList<User>()
        volleyRequest = Volley.newRequestQueue(this)

        getUsersArray(stringLink)
    }

    private fun getUsersArray(url: String) {
        val jsonArrayReq = JsonArrayRequest(Request.Method.GET, url,
                            Response.Listener {
                                response: JSONArray ->
                                try {
                                    Log.d("======>", response.toString())

                                    for (i in 0..response.length() -1) {
                                        val userObj = response.getJSONObject(i)

                                        var showName = userObj.getString("name")
                                        var showUserName = userObj.getString("username")
                                        var showEmail = userObj.getString("email")

                                        var user = User()

                                        user.name = showName
                                        user.username = showUserName
                                        user.email = showEmail

                                        arrayList!!.add(user)

                                        userAdater = UserListAdapter(arrayList!!, this)
                                        layoutManayer = LinearLayoutManager(this)

                                        reciclerUser!!.layoutManager = layoutManayer
                                        reciclerUser!!.adapter = userAdater
                                    }

                                    userAdater!!.notifyDataSetChanged()

                                }catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            },
                            Response.ErrorListener {  })

        volleyRequest!!.add(jsonArrayReq)
    }
}