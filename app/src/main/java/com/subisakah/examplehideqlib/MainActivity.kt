package com.subisakah.examplehideqlib

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OpenJavaWebViewButton.setOnClickListener {
            val intent = Intent(baseContext, JavaWebViewExample::class.java)
            startActivity(intent)
        }

        OpenKotlinWebViewButton.setOnClickListener {
            val intent = Intent(baseContext, KotlinWebViewExample::class.java)
            startActivity(intent)
        }
    }
}