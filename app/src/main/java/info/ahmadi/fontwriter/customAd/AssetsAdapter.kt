package info.ahmadi.fontwriter.customAd

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kdownloader.KDownloader
import com.squareup.picasso.Picasso
import info.ahmadi.fontwriter.databinding.AdapterAssetsBinding
import info.ahmadi.fontwriter.databinding.DialogDownloadBinding
import info.ahmadi.fontwriter.model.AssetsApiResponseData
import java.io.File
import javax.inject.Inject

class AssetsAdapter @Inject constructor() : RecyclerView.Adapter<AssetsAdapter.View>() {

    private var items: ArrayList<AssetsApiResponseData> = ArrayList()

    inner class View(private val binding: AdapterAssetsBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val kDownloader = KDownloader.create(context)
        private var dirPath = "/storage/emulated/0/Download/fontwriter/assets"
        private lateinit var downloadedFile: File

        fun onCreate(position: Int) {
            Picasso.get().load(items[position].link).into(binding.image)
            binding.placeHolder.setOnClickListener {
                val fileName = items[position].name
                val dialogBinding = DialogDownloadBinding.inflate(LayoutInflater.from(context))
                val dialog = MaterialAlertDialogBuilder(this.context)
                    .setView(dialogBinding.root)
                    .create()
                downloadedFile = File(dirPath, fileName)
                Picasso.get().load(items[position].link).into(dialogBinding.imageView)
                dialogBinding.saveImage.setOnClickListener {
                    if (this.downloadedFile.exists()) {
                        Toast.makeText(dialogBinding.root.context, "ذخیره شد", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()
                    } else {
                        this.downloadFile(position, true)
                    }
                }
                dialog.show()
            }
        }


        private fun downloadFile(position: Int, isDownload: Boolean = false) {
            val url = items[position].link
            val fileName = items[position].name
            val req = kDownloader.newRequestBuilder(url, dirPath, fileName)
                .build()
            kDownloader.enqueue(req, onStart = {}, onCompleted = {
                Toast.makeText(context, "ذخیره شد", Toast.LENGTH_SHORT).show()
            }, onError = {
                Toast.makeText(context, "مشکل در دریافت فایل", Toast.LENGTH_SHORT).show()
            })
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetsAdapter.View {
        val binding = AdapterAssetsBinding.inflate(LayoutInflater.from(parent.context))
        return View(binding, parent.context)
    }

    override fun onBindViewHolder(holder: AssetsAdapter.View, position: Int) {
        holder.onCreate(position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItem: List<AssetsApiResponseData>) {
        this.items.clear()
        this.items.addAll(newItem)
        notifyDataSetChanged()
    }
}
