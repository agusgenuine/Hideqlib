package com.subisakah.hideqlib

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ServerLog{
    companion object {

        /**
         * @param context Context of application
         * @param params Post parameter from url
         * @param apiResponse Listener response from the website
         * @see ApiResponse
         */
        @JvmStatic
        fun post(context: Context, urlServer: String, params:HashMap<String, String>, apiResponse: ApiResponse) {

            val url = "https://ip.seeip.org/jsonip"
            val stringRequest = StringRequest(url, Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val ip = jsonObject.get("ip") as String
                    val queue = Volley.newRequestQueue(context)
                    val postRequest = object : StringRequest(Request.Method.POST, urlServer,
                        Response.Listener<String> {
                            apiResponse.onSuccess(it)
                        },
                        Response.ErrorListener {
                            apiResponse.onSuccess(it)
                        }) {
                        override fun getParams() : Map<String, String> {
                            params["PuIP"] = ip
                            return params
                        }
                    }
                    queue.add(postRequest)
                } catch (e: JSONException) {
                    apiResponse.onError(e)
                }
            }, Response.ErrorListener {error ->
                apiResponse.onSuccess(error)
            })
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
}