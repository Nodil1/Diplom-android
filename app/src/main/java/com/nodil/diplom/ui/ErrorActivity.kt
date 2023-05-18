package com.nodil.diplom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nodil.diplom.R
import kotlin.system.exitProcess

class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        MaterialAlertDialogBuilder(this)
            .setTitle("емае")
            .setMessage(intent.getStringExtra("error"))
            .setPositiveButton("ну и ну") { dialog, _ ->
                exitProcess(1)
            }
            .show()
    }
}