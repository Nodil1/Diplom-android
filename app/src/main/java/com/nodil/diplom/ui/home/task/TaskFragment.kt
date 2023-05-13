package com.nodil.diplom.ui.home.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.nodil.diplom.databinding.FragmentTaskBinding
import com.nodil.diplom.domain.enums.WorkerAction
import com.nodil.diplom.domain.enums.WorkerStatus
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.views.ViewTask
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val vm: TaskViewModel by viewModel()
    private val homeViewModel: HomeViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        observers()
        vm.loadMyTasks()

        return binding.root
    }

    private fun observers() {
        vm.myTask.observe(viewLifecycleOwner) {
            it.onEach { taskModel ->
                val taskView = ViewTask(requireActivity(), null)
                taskView.setTaskModel(taskModel)
                binding.taskList.addView(taskView)
                taskView.onStartTask = {_it ->
                    println(homeViewModel.status.value)
                    if (homeViewModel.status.value !== WorkerStatus.WORKING) {
                        Snackbar.make(binding.root, "У вас уже есть задание, которое вы выполняете", Snackbar.LENGTH_SHORT)
                            .show()
                    } else {
                        homeViewModel.changeStatus(WorkerStatus.GO_TO_TASK, _it.id)
                        homeViewModel.setCurrentTask(_it)
                        homeViewModel.setPage(0)
                        Snackbar.make(binding.root, "Вы начали выполнять задание", Snackbar.LENGTH_SHORT)

                    }
                }
            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            TaskFragment()
    }
}