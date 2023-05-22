package com.nodil.diplom.util

import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.IOException

fun Context.checkGPS() {
    val locationManager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        MaterialAlertDialogBuilder(this)
            .setTitle("Внимание!")
            .setMessage("Для работы приложения необходимо включить геолокацию")
            .setNegativeButton("Отмена") { _, _ ->

            }
            .setPositiveButton("Перейти в настройки") { _, _ ->
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(callGPSSettingIntent)

            }
            .show()
    }
}
fun Context.getFilesFromInternalStorageFolder(folderName: String): List<String> {
    val context = this
    val internalStoragePath = context.filesDir.path
    val folderPath = "$internalStoragePath/$folderName"

    val folder = File(folderPath)

    println(folderPath)
    if (folder.exists() && folder.isDirectory) {
        println("Prikol")

        return folder.listFiles()?.map { file -> file.name } ?: emptyList()
    }

    return emptyList()
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}