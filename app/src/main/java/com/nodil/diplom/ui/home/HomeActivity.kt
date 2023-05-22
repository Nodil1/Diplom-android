package com.nodil.diplom.ui.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.util.AttributeSet
import android.view.View
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
    private fun checkPermission() {
        if (!permissionChecker.check(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionChecker.request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                111
            )
        }
        if (!permissionChecker.check(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionChecker.request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                123
            )
        }
        if (!permissionChecker.check(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionChecker.request(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ),
                    111
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //checkGPS()

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
            if (it) {
                dialog.dismiss()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        viewPager.offscreenPageLimit = 5
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        observers()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        checkPermission()
        return super.onCreateView(name, context, attrs)

    }
    private fun observers() {
        homeViewModel.currentPage.observe(this) {
            viewPager.currentItem = it
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty()) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Ошибка")
                .setMessage("Для корректной работы приложения необходимо дать все разрешения")
                .setPositiveButton("Хорошо") { _, _ -> checkPermission() }

        }
    }


}