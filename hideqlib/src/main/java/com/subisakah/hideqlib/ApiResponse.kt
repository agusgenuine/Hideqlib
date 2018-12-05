package com.subisakah.hideqlib

import java.lang.Exception

interface ApiResponse {
    fun onSuccess(response: Any)
    fun onError(exception: Exception)
}