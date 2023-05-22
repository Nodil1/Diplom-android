package com.nodil.diplom.ui.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.location.Location
import android.os.*
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.nodil.diplom.domain.models.GeoModel
import com.nodil.diplom.domain.repositories.TaskRepository
import com.nodil.diplom.domain.usecase.auth.GetMyIdUseCase
import com.nodil.diplom.domain.usecase.geo.SaveGeoUseCase
import com.nodil.diplom.util.KalmanFilter
import com.nodil.diplom.util.LastLocationHolder
import com.nodil.diplom.util.LocationFilter
import com.nodil.diplom.util.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.Calendar

class LocationService : Service() {
    private val kalmanFilter = KalmanFilter()
    private val saveGeoUseCase: SaveGeoUseCase by inject()
    private val getMyIdUseCase: GetMyIdUseCase by inject()
    private val taskRepository: TaskRepository by inject()
    private val locationFilter = LocationFilter()

    companion object {
        const val CHANNEL_ID = "LocationServiceChannel"
    }

    private lateinit var locationClient: FusedLocationProviderClient
    private var lastLocation: Location? = null
    override fun onCreate() {
        super.onCreate()

        subscribeToEvents()
        startForeground()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        initLocationClient()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    private fun subscribeToEvents() {
        taskRepository.subscribe(getMyIdUseCase.execute()) {
            NotificationHelper(this).showNotification("Новое задание ${it.name}", it.description)

        }
        taskRepository.subscribeNotify(getMyIdUseCase.execute()) {
            NotificationHelper(this).showNotification("Сообщение от менеджера", it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initLocationClient() {
        locationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000)
            .setWaitForAccurateLocation(true)
            .setMinUpdateDistanceMeters(3f)
            .build()

        locationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            location ?: return
            val result = locationFilter.check(location)
            println(result)
            if (result) {
                LastLocationHolder.setLocation(location)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        saveGeoUseCase.execute(
                            GeoModel(
                                location.latitude,
                                location.longitude,
                                Calendar.getInstance().time.toString()
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            showNotification(location)
        }
    }

    private fun stopLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }

    private fun showNotification(location: Location) {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentTitle("Current Location")
            .setContentText("Lat: ${location.latitude}, Lon: ${location.longitude} Acc: ${location.accuracy}")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(false)
            .setOngoing(true)

        val notification: Notification = notificationBuilder.build()
        startForeground(1, notification)

    }

    private fun startForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Location"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("Tracking current location...")
            .build()

        startForeground(1, notificationBuilder)
    }

}
