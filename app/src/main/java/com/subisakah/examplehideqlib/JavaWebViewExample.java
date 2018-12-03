package com.subisakah.examplehideqlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.subisakah.hideqlib.WebViewProxy;

public class JavaWebViewExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_web_view_example);
        this.setTitle(this.getClass().getSimpleName());

        // have used a proxy
        Log.i(this.getClass().getSimpleName(), String.valueOf(WebViewProxy.isUsingProxy(getApplicationContext())));
    }
}
