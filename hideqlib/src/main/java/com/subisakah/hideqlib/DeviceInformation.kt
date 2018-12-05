package com.subisakah.hideqlib

import java.net.NetworkInterface
import java.util.*
import android.os.Build
import com.subisakah.hideqlib.InfoKey.Companion.DEVICE_NAME
import com.subisakah.hideqlib.InfoKey.Companion.MAC_ADDRESS
import com.subisakah.hideqlib.InfoKey.Companion.OS_VERSION
import com.subisakah.hideqlib.InfoKey.Companion.PRIVATE_IP4
import com.subisakah.hideqlib.InfoKey.Companion.PRIVATE_IP6
import kotlin.collections.HashMap

class DeviceInformation {

    companion object {


        /**
         * Get device information
         * @return Return map of device information
         * @See InfoKey
         */
        @JvmStatic
        fun map() : HashMap<String, String> {
            val value = HashMap<String, String>()
            value[PRIVATE_IP4] = getPrivateIP(useIPv4 = true).toString()
            value[PRIVATE_IP6] = getPrivateIP(useIPv4 = false).toString()
            value[OS_VERSION] = getOs()
            value[DEVICE_NAME] = getDeviceName()
            value[MAC_ADDRESS] = getMACAddress(iface = "wlan0")
            return value
        }

        private fun getPrivateIP(useIPv4:Boolean): String? {
            try {
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    val addrs = Collections.list(intf.inetAddresses)
                    for (addr in addrs) {
                        if (!addr.isLoopbackAddress) {
                            val sAddr = addr.hostAddress
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            val isIPv4 = sAddr.indexOf(':') < 0

                            if (useIPv4) {
                                if (isIPv4)
                                    return sAddr
                            } else {
                                if (!isIPv4) {
                                    val delim = sAddr.indexOf('%') // drop ip6 zone suffix
                                    return if (delim < 0) sAddr.toUpperCase() else sAddr.substring(0, delim).toUpperCase()
                                }
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return ""

        }

        private fun getOs(): String {
            val release = Build.VERSION.RELEASE
            val sdkVersion = Build.VERSION.SDK_INT
            return "Android ($release), SDK $sdkVersion"
        }

        private fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

        private fun capitalize(s: String?): String {
            if (s == null || s.isEmpty()) {
                return ""
            }
            val first = s[0]
            return if (Character.isUpperCase(first)) {
                s
            } else {
                Character.toUpperCase(first) + s.substring(1)
            }
        }

        private fun getMACAddress(iface: String?): String {
            try {
                val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
                for (intf in interfaces) {
                    if (iface != null) {
                        if (!intf.name.equals(iface, ignoreCase = true)) continue
                    }
                    val mac = intf.hardwareAddress ?: return ""
                    val buf = StringBuilder()
                    for (aMac in mac) buf.append(String.format("%02X:", aMac))
                    if (buf.isNotEmpty()) buf.deleteCharAt(buf.length - 1)
                    return buf.toString()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return ""
        }

    }
}