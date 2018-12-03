package com.subisakah.examplehideqlib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.subisakah.hideqlib.WebViewProxy

class KotlinWebViewExample : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_web_view_example)
        this.title = this.javaClass.simpleName

        // have used a proxy
        Log.i(this.javaClass.simpleName, WebViewProxy.isUsingProxy(context = applicationContext).toString())
    }
}
