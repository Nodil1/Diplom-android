package com.nodil.diplom.ui.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.nodil.diplom.BaseActivity
import com.nodil.diplom.databinding.ActivityHomeBinding
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.ui.home.ui.main.SectionsPagerAdapter
import com.nodil.diplom.ui.services.CustomContentObserver
import com.nodil.diplom.util.PermissionChecker
import com.nodil.diplom.util.checkGPS
import com.nodil.diplom.util.getFilesFromInternalStorageFolder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {
    private val permissionChecker = PermissionChecker(this)

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: ViewPager
    private val homeViewModel: HomeViewModel by viewModel()
    override fun onResume() {
        super.onResume()
        checkGPS()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Загрузка...")
            .setMessage("Дождитесь загрузки приложения")
            .setCancelable(false)
            .show()
        homeViewModel.ready.observe(this) {
            if (it){
                dialog.dismiss()
            }
        }
        permissionChecker.request(
            Manifest.permission.ACCESS_FINE_LOCATION,
            100
        )
        permissionChecker.request(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            100
        )
        permissionChecker.request(
            Manifest.permission.ACCESS_NETWORK_STATE,
            100
        )
        permissionChecker.request(
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            100
        )
        permissionChecker.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            100
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionChecker.request(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                111
            )
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        observers()
    }

    private fun observers(){
        homeViewModel.currentPage.observe(this) {
            viewPager.currentItem = it
        }
    }


}