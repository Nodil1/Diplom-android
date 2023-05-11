package com.nodil.diplom

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nodil.diplom.di.dataModule
import com.nodil.diplom.di.domainModule
import com.nodil.diplom.di.presentationModule
import com.nodil.diplom.ui.ErrorActivity
import com.nodil.diplom.ui.login.LoginActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.system.exitProcess

val appModule = listOf(domainModule, dataModule, presentationModule)
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
        Thread.setDefaultUncaughtExceptionHandler(MyUncaughtExceptionHandler())
    }
    private inner class MyUncaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, ex: Throwable) {
            Log.e("MyApplication", "Unhandled exception", ex)


            val intent = Intent(applicationContext, ErrorActivity::class.java)
            intent.putExtra("error", ex.stackTraceToString())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }
}