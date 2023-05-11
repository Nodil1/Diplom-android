package com.nodil.diplom.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.nodil.diplom.databinding.ViewTaskBinding
import com.nodil.diplom.domain.models.TaskModel

class ViewTask(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private val binding = ViewTaskBinding.inflate(LayoutInflater.from(context), this, true)
    var model: TaskModel? = null
    fun setTaskModel(model: TaskModel) {
        this.model = model
        binding.head.text = model.name
        binding.subhead.text = model.customer
        binding.desc.text = model.description
    }

}