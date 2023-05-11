package com.nodil.diplom.ui.home.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nodil.diplom.databinding.FragmentTaskBinding
import com.nodil.diplom.ui.home.HomeViewModel
import com.nodil.diplom.ui.views.ViewTask
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val vm: TaskViewModel by activityViewModel()
    private val homeViewModel: HomeViewModel by inject()

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
            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            TaskFragment()
    }
}