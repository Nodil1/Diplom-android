package com.nodil.diplom.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionChecker(private val context: Context) {

    fun check(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun request(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            (context as AppCompatActivity),
            arrayOf(permission),
            requestCode
        )
    }
    fun request(permission: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            (context as AppCompatActivity),
            permission,
            requestCode
        )
    }
    fun shouldShowRequestRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            (context as AppCompatActivity),
            permission
        )
    }

}