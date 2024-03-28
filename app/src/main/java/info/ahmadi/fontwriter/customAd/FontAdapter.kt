package info.ahmadi.fontwriter.customAd

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kdownloader.KDownloader
import com.squareup.picasso.Picasso
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.AdapterFontBinding
import info.ahmadi.fontwriter.model.FontApiResponseData
import java.io.File
import javax.inject.Inject

class FontAdapter @Inject constructor() : RecyclerView.Adapter<FontAdapter.View>() {
    private var items: ArrayList<FontApiResponseData> = ArrayList()
    private var available = false
    private val dir = "/storage/emulated/0/Download/fontwriter/fonts"
    private var controller : Controller? = null
    inner class View(private val binding: AdapterFontBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val kDownloader = KDownloader.create(context)
        fun onCreate(position: Int) {
            Picasso.get().load(items[position].image).into(binding.image)
            binding.name.text = items[position].name
            binding.placeHolder.setOnClickListener {
                val fontList = File(dir)
                val font = File(fontList,items[position].name)
                available = font.exists()
                if (available){
                    controller?.changeTextFont(Typeface.createFromFile("${dir}/${items[position].name}"))
                }else{
                    downloadFile(position)
                }
            }
        }
        private fun downloadFile(position: Int){
            val request = kDownloader.newRequestBuilder(
                items[position].font,
                dir,
                items[position].name
            ).build()

            kDownloader.enqueue(request, onStart = {
                Toast.makeText(context, "درحال دریافت فایل", Toast.LENGTH_SHORT).show()
            }, onCompleted = {
                Toast.makeText(context, "اتمام دریافت", Toast.LENGTH_SHORT).show()
                controller?.changeTextFont(Typeface.createFromFile("${dir}/${items[position].name}"))

            }, onError = {
                Toast.makeText(context, "مشکل در دریافت فایل", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontAdapter.View {
        val binding = AdapterFontBinding.inflate(LayoutInflater.from(parent.context))
        return View(binding, parent.context)
    }

    override fun onBindViewHolder(holder: FontAdapter.View, position: Int) {
        holder.onCreate(position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItem: ArrayList<FontApiResponseData>) {
        this.items.clear()
        this.items.addAll(newItem)
        notifyDataSetChanged()
    }

    fun setController(controller: Controller) {
        this.controller = controller
    }

}