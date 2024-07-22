package info.ahmadi.fontwriter.view

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.AssetsActivity
import info.ahmadi.fontwriter.HelperActivity
import info.ahmadi.fontwriter.R
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.FontAdapter
import info.ahmadi.fontwriter.databinding.ActivityHomeBinding
import info.ahmadi.fontwriter.databinding.DialogContactBinding
import info.ahmadi.fontwriter.model.FontApiResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject


@ActivityScoped
class ViewHomeActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context), Controller {
    val binding = ActivityHomeBinding.inflate(LayoutInflater.from(context))
    private var align: Layout.Alignment = Layout.Alignment.ALIGN_CENTER

    private var stateGravity = 0

    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var adapter: FontAdapter

    @Inject
    lateinit var controller: Controller

    @Inject
    lateinit var fragment: FragmentManager

    private var fontResponseData: ArrayList<FontApiResponseData>? = null
    private var fontIsEnglish = false
    private var saveColor = 0xFF000000
    fun saveText() {
        binding.export.setOnClickListener {
            if (binding.text.text.isNotEmpty()) {
                val text = binding.text.text.toString()
                val width = binding.text.width

                val textPaint = TextPaint().apply {
                    color = binding.text.currentTextColor
                    textSize = binding.text.textSize
                    typeface = binding.text.typeface
                }

                val textLayout = StaticLayout.Builder.obtain(
                    text, 0, text.length, textPaint, width
                )
                    .setAlignment(align)
                    .setLineSpacing(0f, 1f)
                    .build()

                val bitmap = Bitmap.createBitmap(width, textLayout.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)

                textLayout.draw(canvas)

                saveBitmapToScopedStorage(bitmap)

            } else {
                Toast.makeText(context, "متنی برای ذخیره سازی وجود ندارد", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun onContactClick() {
        binding.contact.setOnClickListener {
            val dialogBinding = DialogContactBinding.inflate(LayoutInflater.from(context))
            val dialog = MaterialAlertDialogBuilder(context)
                .setView(dialogBinding.root)
                .create()
            dialogBinding.instagram.setOnClickListener {
                openLink(
                    "https://instagram.com/Lrn.ir"
                )
            }
            dialogBinding.site.setOnClickListener {
                openLink("https://alirezaahmadi.info/")
            }
            dialog.show()
        }
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }

    private fun saveBitmapToScopedStorage(bitmap: Bitmap) {
        val contentResolver: ContentResolver = context.contentResolver
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "fontwriter")
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        try {
            val os = contentResolver.openOutputStream(imageUri ?: Uri.EMPTY)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os!!)
            os.close()
            Toast.makeText(context, "ذخیره شد", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "خطا در ذخیره سازی", Toast.LENGTH_SHORT).show()
        }
    }

    fun onHelperClick() {
        binding.helper.setOnClickListener {
            context.startActivity(Intent(context, HelperActivity::class.java))
        }
    }

    fun onStartUp() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                api.getFonts().awaitResponse()
            } catch (_: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, retry = {
                        onStartUp()
                    }, exit = {
                        controller.finishFromController()
                    })
                }
                return@launch
            }
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progress.visibility = View.INVISIBLE
                    binding.main.visibility = View.VISIBLE
                    adapter.setController(this@ViewHomeActivity)
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager =
                        LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    fontResponseData = response.body()?.fonts
                    adapter.updateData(fontResponseData?.toList() ?: listOf())
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, retry = {
                        onStartUp()
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }

    fun changeFontButtonClick() {
        binding.changeFont.setOnClickListener {
            if (fontIsEnglish) {
                adapter.updateData(fontResponseData?.filter { it.type == "fa" } ?: arrayListOf())
            } else {
                adapter.updateData(fontResponseData?.filter { it.type == "en" } ?: arrayListOf())
            }
            fontIsEnglish = !fontIsEnglish
        }
    }

    fun changeTextColor() {
        binding.changeColor.setOnClickListener {
            ColorPickerDialog()
                .withColor(saveColor.toInt())
                .withListener { _, color ->
                    saveColor = color.toLong()
                    binding.text.setTextColor(color)
                }
                .show(fragment, "colorPicker")
        }
    }

    fun changeTextSize() {
        binding.textSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.text.textSize = (progress * 1.2).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.i("tracker", "onStartTrackingTouch: start tracking")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i("tracker", "onStartTrackingTouch: stop tracking")
            }
        })
    }

    override fun changeState(state: Boolean) {
        if (state) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.INVISIBLE
        }
    }

    fun clearText() {
        binding.delete.setOnClickListener {
            binding.text.text = Editable.Factory.getInstance().newEditable("")
        }
    }

    fun onAssetsClick() {
        binding.emoji.setOnClickListener {
            context.startActivity(Intent(context, AssetsActivity::class.java))
        }
    }

    fun changeGravity() {
        binding.gravity.setOnClickListener {
            stateGravity = (stateGravity + 1) % 3
            binding.text.gravity = when (stateGravity) {
                0 -> {
                    align = Layout.Alignment.ALIGN_CENTER
                    Gravity.CENTER
                }

                1 -> {
                    align = Layout.Alignment.ALIGN_NORMAL
                    Gravity.START
                }

                2 -> {
                    align = Layout.Alignment.ALIGN_OPPOSITE
                    Gravity.END
                }

                else -> {
                    align = Layout.Alignment.ALIGN_CENTER
                    Gravity.CENTER
                }
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
    }
}