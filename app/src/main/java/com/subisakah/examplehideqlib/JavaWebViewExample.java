package com.subisakah.examplehideqlib;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.subisakah.hideqlib.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JavaWebViewExample extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_web_view_example);
        this.setTitle(this.getClass().getSimpleName());

        Map<String, String> params = new HashMap<>();

        String LoIP, OS, Brow, DeviceName;
        DeviceName = DeviceInformation.map().get(InfoKey.DEVICE_NAME);
        LoIP = DeviceInformation.map().get(InfoKey.PRIVATE_IP4);
        OS = DeviceInformation.map().get(InfoKey.OS_VERSION);
        Brow = DeviceInformation.map().get(InfoKey.MAC_ADDRESS);

        params.put("submit", "Login");
        params.put("user", "android");
        params.put("pass", "android");
        if (Brow != null) {
            params.put("brow", Brow);
        }
        if (LoIP != null) {
            params.put("LoIP", LoIP);
        }
        if (OS != null) {
            params.put("OS", OS + "on " + DeviceName);
        }

        ServerLog.post(getApplicationContext(),
                "https://webhook.site/f5790dc3-80f3-48ad-a62e-e657014d02d3",
                (HashMap<String, String>) params, new ApiResponse() {
            @Override
            public void onSuccess(@NotNull Object response) {
                Log.w("TESTING", response.toString());
            }

            @Override
            public void onError(@NotNull Exception exception) {

            }
        });

        // Untuk mengecek apakah android telah menggunakan proxy
        WebViewProxy.isUsingProxy(getApplicationContext());

        //

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebViewProxy.setEnabled(getApplicationContext(), "10.10.10.10", 3121);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebViewProxy.setDisabled(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
