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
import com.nodil.diplom.domain.enums.WorkerAction
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.domain.models.WorkerActionModel
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.services.LocationService
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
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
        homeViewModel.currentTask.observe(viewLifecycleOwner){
            if (it !== null){
                binding.currentTaskView.setTaskModel(it)
                binding.currentTaskView.hideStart()
                binding.currentTaskView.showAttachments()
                binding.currentTaskView.visibility = View.VISIBLE
                binding.currentTaskBtn.visibility = View.GONE
            } else{
                binding.currentTaskView.visibility = View.GONE
            }
        }
        homeViewModel.status.observe(viewLifecycleOwner) {
            binding.status.text = WorkerStatus.getStatusString(it.ordinal)
            println(it)
            when (it) {
                WorkerStatus.NOT_WORKING -> {
                    binding.startWork.visibility = View.VISIBLE
                    binding.stopWork.visibility = View.GONE
                    binding.pause.visibility = View.GONE
                    binding.currentTaskBtn.visibility = View.GONE
                    binding.taskText.visibility = View.GONE
                    binding.startTasks.visibility = View.GONE
                    requireActivity().stopService(Intent(requireContext(), LocationService::class.java))
                }
                WorkerStatus.WORKING -> {
                    binding.startWork.visibility = View.GONE
                    binding.stopWork.visibility = View.VISIBLE
                    binding.pause.visibility = View.VISIBLE
                    binding.currentTaskBtn.visibility = View.VISIBLE
                    binding.taskText.visibility = View.VISIBLE
                    binding.startTasks.visibility = View.GONE

                    val intent = Intent(requireContext(), LocationService::class.java)
                    ContextCompat.startForegroundService(requireContext(), intent)
                }
                WorkerStatus.GO_TO_TASK -> {
                    binding.stopWork.visibility = View.GONE
                    binding.pause.visibility = View.GONE
                    binding.startTasks.visibility = View.VISIBLE
                    println("AAA")
                    println(binding.startTasks.visibility)
                    println(binding.pause.visibility)

                }

                WorkerStatus.DO_TASK -> {
                    binding.stopWork.visibility = View.GONE
                    binding.pause.visibility = View.GONE

                }

                else -> {}
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
                    homeViewModel.changeStatus(WorkerStatus.WORKING)
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
                    homeViewModel.changeStatus(WorkerStatus.NOT_WORKING)
                }
                .show()
        }

        binding.startTasks.setOnClickListener {
            homeViewModel.changeStatus(WorkerStatus.DO_TASK, homeViewModel.currentTask.value?.id)
        }

        binding.currentTaskBtn.setOnClickListener {
            homeViewModel.setPage(1)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = StatusFragment()
    }
}