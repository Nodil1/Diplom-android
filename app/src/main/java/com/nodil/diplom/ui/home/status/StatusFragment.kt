package com.nodil.diplom.ui.home.status

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nodil.diplom.databinding.FragmentStatusBinding
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.services.LocationService
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
    private val vm: StatusViewModel by viewModel()
    private val homeViewModel: HomeViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStatusBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observers()
        clickListeners()
        return binding.root
    }

    private fun observers() {
        vm.status.observe(viewLifecycleOwner) {
            binding.status.text = WorkerStatus.getStatusString(it)

            when (it) {
                0 -> {
                    binding.startWork.visibility = View.VISIBLE
                    binding.stopWork.visibility = View.GONE
                    binding.pause.visibility = View.GONE
                    binding.currentTask.visibility = View.INVISIBLE
                    binding.taskText.visibility = View.INVISIBLE
                    requireActivity().stopService(Intent(requireContext(), LocationService::class.java))
                }
                1 -> {
                    binding.startWork.visibility = View.GONE
                    binding.stopWork.visibility = View.VISIBLE
                    binding.pause.visibility = View.VISIBLE
                    binding.currentTask.visibility = View.VISIBLE
                    binding.taskText.visibility = View.VISIBLE
                    val intent = Intent(requireContext(), LocationService::class.java)
                    ContextCompat.startForegroundService(requireContext(), intent)
                }


            }
        }
    }

    private fun clickListeners() {
        binding.startWork.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Начать смену?")
                .setNegativeButton("Отмена") { _, _ ->
                    // Respond to negative button press
                }
                .setPositiveButton("Да") { _, _ ->
                    vm.changeStatus(1)
                }
                .show()
        }
        binding.stopWork.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Закончить смену?")
                .setNegativeButton("Отмена") { _, _ ->
                    // Respond to negative button press
                }
                .setPositiveButton("Да") { _, _ ->
                    vm.changeStatus(0)
                }
                .show()
        }

        binding.currentTask.setOnClickListener {
            homeViewModel.setPage(1)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = StatusFragment()
    }
}