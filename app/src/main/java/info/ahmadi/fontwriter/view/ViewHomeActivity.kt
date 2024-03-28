package info.ahmadi.fontwriter.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.AssetsActivity
import info.ahmadi.fontwriter.R
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.FontAdapter
import info.ahmadi.fontwriter.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog
import retrofit2.awaitResponse
import java.io.File
import javax.inject.Inject


@ActivityScoped
class ViewHomeActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context), Controller {
    val binding = ActivityHomeBinding.inflate(LayoutInflater.from(context))
    private var stateGravity = 0
    private var stateStyle = 0

    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var adapter: FontAdapter

    // save text in storage
    fun saveText() {
        binding.export.setOnClickListener {
            val paint = Paint()

            val text = binding.text.text.toString()
            val textBounds = Rect()
            paint.getTextBounds(text, 0, text.length, textBounds)

            val bitmap = Bitmap.createBitmap(
                binding.text.width,
                binding.text.height,
                Bitmap.Config.ARGB_8888
            )
            paint.color = binding.text.currentTextColor
            paint.textSize = binding.text.textSize
            paint.typeface = binding.text.typeface

            val canvas = Canvas(bitmap)
            val canvasWidth = canvas.width
            val canvasHeight = canvas.height

            val textWidth = paint.measureText(text)
            val textX = (canvasWidth - textWidth) / 2
            val textY = (canvasHeight + textBounds.height()) / 2

            canvas.drawText(text, textX, textY.toFloat(), paint)

            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "fontwriter",
                "fontwriter"
            )
            Toast.makeText(context, "ذخیره شد", Toast.LENGTH_SHORT).show()
        }
    }
    // get font list
    fun onStartUp(controller: Controller) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getFonts().awaitResponse()
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progress.visibility = View.INVISIBLE
                    binding.main.visibility = View.VISIBLE
                    adapter.setController(this@ViewHomeActivity)
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter.updateData(response.body()!!.fonts)
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, retry = {
                        onStartUp(controller)
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }

    // change text color with color picker dialog
    fun changeTextColor(fragment: FragmentManager) {
        binding.changeColor.setOnClickListener {
            ColorPickerDialog()
                .withListener { _, color ->
                    binding.text.setTextColor(color)
                }
                .show(fragment, "colorPicker")
        }
    }

    // change text size with seek bar
    fun changeTextSize() {
        binding.textSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.text.textSize = (progress * 2).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.i("tracker", "onStartTrackingTouch: start tracking")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i("tracker", "onStartTrackingTouch: stop tracking")
            }
        })
    }

    // clear text box
    fun clearText() {
        binding.delete.setOnClickListener {
            binding.text.text = Editable.Factory.getInstance().newEditable("")
        }
    }

    // go to assets activity
    fun onAssetsClick() {
        binding.emoji.setOnClickListener {
            context.startActivity(Intent(context, AssetsActivity::class.java))
        }
    }

    // change gravity text box
    @SuppressLint("RtlHardcoded")
    fun changeGravity() {
        binding.gravity.setOnClickListener {
            stateGravity = (stateGravity + 1) % 3
            binding.text.gravity = when (stateGravity) {
                0 -> Gravity.CENTER
                1 -> Gravity.RIGHT
                2 -> Gravity.LEFT
                else -> Gravity.CENTER
            }
            binding.gravity.setImageDrawable(
                when (stateGravity) {
                    0 -> ContextCompat.getDrawable(context, R.drawable.ic_center)
                    1 -> ContextCompat.getDrawable(context, R.drawable.ic_right)
                    2 -> ContextCompat.getDrawable(context, R.drawable.ic_left)
                    else -> ContextCompat.getDrawable(context, R.drawable.ic_center)
                }
            )
        }
    }

    override fun changeTextFont(typeface: Typeface) {
        binding.text.typeface = typeface
        Toast.makeText(context, "فونت مدنظر اعمال شد ", Toast.LENGTH_SHORT).show()
    }
}