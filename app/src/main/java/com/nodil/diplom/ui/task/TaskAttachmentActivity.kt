package com.nodil.diplom.ui.task

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.nodil.diplom.databinding.ActivityTaskAttachmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


class TaskAttachmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskAttachmentBinding
    private lateinit var loadingDialog : androidx.appcompat.app.AlertDialog
    private val vm : TaskAttachmentViewModel by viewModel()
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    val inputData =
                        contentResolver.openInputStream(fileUri)?.readBytes()!!
                    vm.addImage(inputData)
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
        observers()
        vm.setId(intent.getIntExtra("idTask", 0))
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Вложения к задаче"
    }

    private fun observers(){
        vm.image.observe(this){
            println(it)
            for (i in binding.images.childCount - 1 downTo 1){
                println(i)
                binding.images.removeViewAt(i)
            }
            it.onEach { img ->
                addImage(img)
            }
        }
        vm.loadingEnd.observe(this){
            if (it){
                loadingDialog.dismiss()
                Snackbar.make(binding.root, "Загрузка завершена", Snackbar.LENGTH_SHORT).show()
                finish()
            }

        }
    }
    private fun clickListeners(){
        binding.card.setOnClickListener {
            ImagePicker.with(this)

                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.clearBtn.setOnClickListener {
            binding.drawSign.clear()
        }
        binding.save.setOnClickListener {
            loadingDialog = MaterialAlertDialogBuilder(this@TaskAttachmentActivity)
                .setTitle("Загрузка...")
                .setMessage("Дождитесь загрузки изображений")
                .setCancelable(false)
                .show()
            val bitmap: Bitmap = binding.drawSign.extraBitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            vm.addSign(byteArray)
            vm.save()
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