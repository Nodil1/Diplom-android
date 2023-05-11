package com.nodil.diplom.ui.tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nodil.diplom.BaseActivity
import com.nodil.diplom.databinding.ActivityTrackerBinding

class TrackerActivity : BaseActivity() {
    private lateinit var binding: ActivityTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}