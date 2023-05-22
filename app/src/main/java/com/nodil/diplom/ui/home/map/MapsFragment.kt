package com.nodil.diplom.ui.home.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nodil.diplom.R
import com.nodil.diplom.domain.models.TaskModel
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.views.ViewTask
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MapsFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModel()
    private lateinit var googleMap: GoogleMap
    private var taskObserver: Observer<Array<TaskModel>>? = null
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        googleMap.setOnMarkerClickListener {
            val taskView = ViewTask(requireContext(), null)
            taskView.setTaskModel(it.tag as TaskModel)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Задание")
                .setView(taskView)
                .show()
            true

        }
        observers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun observers() {
        homeViewModel.myTask.observe(viewLifecycleOwner) {
            it.onEach { taskModel ->
                val marker = LatLng(taskModel.latitude.toDouble(), taskModel.longitude.toDouble())
                val googleMarker =
                    googleMap.addMarker(MarkerOptions().position(marker).title(taskModel.name))
                googleMarker!!.tag = taskModel
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))
            }
        }
    }
}