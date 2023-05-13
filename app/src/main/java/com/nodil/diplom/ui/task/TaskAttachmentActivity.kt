package com.nodil.diplom.ui.task

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nodil.diplom.R
import com.nodil.diplom.databinding.ActivityTaskAttachmentBinding

class TaskAttachmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskAttachmentBinding
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    val inputData =
                        contentResolver.openInputStream(fileUri)?.readBytes()!!
                    addImage(inputData)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskAttachmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickListeners()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Вложения к задаче"
    }
    private fun clickListeners(){
        binding.card.setOnClickListener {
            ImagePicker.with(this)

                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

    }
    private fun addImage(byteArray: ByteArray) {

        val image = ImageView(this)
        val layoutParams = LinearLayout.LayoutParams(
            190,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.leftMargin = 20
        image.layoutParams = layoutParams
        image.setOnClickListener {

        }
        binding.images.addView(image)

        Glide.with(this)
            .asBitmap()
            .load(byteArray)
            .transform(CenterCrop(), GranularRoundedCorners(16F, 16F, 16F, 16F))
            .into(image);
    }
}