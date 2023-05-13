package com.nodil.diplom.ui.views

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.nodil.diplom.databinding.ViewTaskBinding
import com.nodil.diplom.domain.models.TaskModel
import com.nodil.diplom.ui.task.TaskAttachmentActivity

class ViewTask( context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val binding = ViewTaskBinding.inflate(LayoutInflater.from(context), this, true)
    var model: TaskModel? = null
    var onStartTask: (TaskModel) -> Unit = {}
    fun setTaskModel(model: TaskModel) {
        this.model = model
        binding.head.text = model.name
        binding.subhead.text = model.customer
        binding.desc.text = model.description
    }

    fun hideStart() {
        binding.startTask.visibility = View.GONE
    }
    fun showAttachments() {
        binding.attachments.visibility = View.VISIBLE
    }
    init {
        binding.startTask.setOnClickListener {
            onStartTask(model!!)
        }
        binding.attachments.setOnClickListener {
            context.startActivity(Intent(context, TaskAttachmentActivity::class.java))
        }
    }

}