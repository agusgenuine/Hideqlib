package com.subisakah.hideqlib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Proxy
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.ArrayMap
import java.lang.Exception
import java.util.*

class WebViewProxy {
    companion object {

        /**
         * This function to check whether you have used a proxy.
         * Must have permission ACCESS_WIFI_STATE and build version in SDK 14 or above
         * @param context context application of activity
         * @return returns result true if using a proxy
         * @see android.permission.ACCESS_WIFI_STATE
         */
        @SuppressLint("ObsoleteSdkInt")
        public fun isUsingProxy(context: Context) : Boolean {
            val isAboveICS: Boolean = Build.VERSION.SDK_INT >= 14
            val proxyAddress: String?
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo

            proxyAddress = if (isAboveICS) {
                System.getProperty("http.proxyHost")
            } else {
                Proxy.getHost(context)
            }
            return !TextUtils.isEmpty(proxyAddress)
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @SuppressLint("PrivateApi")
        @Throws(Exception::class)
        private fun setWebviewProxy(context: Context, host: String, port: Int) {
            System.setProperty("http.proxyHost", host)
            System.setProperty("http.proxyPort", port.toString())
            System.setProperty("https.proxyHost", host)
            System.setProperty("https.proxyPort", port.toString())

            val appClass = Class.forName("android.app.Application")
            val loadApkField = appClass.getDeclaredField("mLoadedApk")
            loadApkField.isAccessible = true

            val loadApk = loadApkField.get(context)
            val loadApkClass = Class.forName("android.app.LoadedApk")
            val receiversField = loadApkClass.getDeclaredField("mReceivers")
            receiversField.isAccessible = true

            val receivers = receiversField.get(loadApk) as ArrayMap<*, *>
            for(receiverMap in receivers.values) {
                for(receiver in (receiverMap as ArrayMap<*, *>).keys) {
                    val jvClass = receiver.javaClass
                    if(jvClass.name.contains("ProxyChangeListener")) {
                        val onReceiveMethod = jvClass.getDeclaredMethod("onReceive", Context::class.java, Intent::class.java)
                        val intent = Intent(Proxy.PROXY_CHANGE_ACTION)
                        val classProxyInfo = Class.forName("android.net.ProxyInfo")
                        val buildDirectProxyMethod = classProxyInfo.getMethod("buildDirectProxy", String.javaClass, Integer.TYPE)
                        val proxyInfo = buildDirectProxyMethod.invoke(classProxyInfo, host, port) as Objects
                        intent.putExtra("proxy", proxyInfo as Parcelable)
                        onReceiveMethod.invoke(receiver, context, intent)
                    }
                }
            }
        }
        /**
         * This function enable proxy in webview.
         * Must build version in SDK 19 or above
         * @param context context application of activity
         */
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @Throws(Exception::class)
        public fun setEnabled(context: Context, host: String, port: Int) {
            WebViewProxy.setWebviewProxy(context, host, port)
        }

        /**
         * This function enable proxy in webview.
         * Must build version in SDK 19 or above
         * @param context context application of activity
         */
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @Throws(Exception::class)
        public fun setDisabled(context: Context) {
            WebViewProxy.setWebviewProxy(context, "", 0)
        }
    }
}