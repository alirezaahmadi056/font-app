package info.ahmadi.fontwriter.customAd

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.kdownloader.KDownloader
import com.squareup.picasso.Picasso
import info.ahmadi.fontwriter.databinding.AdapterAssetsBinding
import info.ahmadi.fontwriter.model.AssetsApiResponseData
import java.io.File
import javax.inject.Inject

class AssetsAdapter @Inject constructor() : RecyclerView.Adapter<AssetsAdapter.View>() {

    private var items: ArrayList<AssetsApiResponseData> = ArrayList()

    inner class View(private val binding: AdapterAssetsBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        private val kDownloader = KDownloader.create(context)
        private val dirPath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path + "/fontwriter"
        private lateinit var downloadedFile:File

        fun onCreate(position: Int) {
            Picasso.get().load(items[position].link).into(binding.image)
            binding.name.text = items[position].name
            binding.placeHolder.setOnClickListener {
                val fileName = items[position].name
                downloadedFile = File(dirPath,fileName)

                if (downloadedFile.exists()) {
                    shareFile(downloadedFile)
                    
                } else {
                    downloadFile(position)
                }
            }
        }
        private fun shareFile(file: File) {
            val uri = FileProvider.getUriForFile(context, "com.example.yourapp.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            try {
                context.startActivity(Intent.createChooser(intent,"اشتراک گذاری"))
            }catch (e:ActivityNotFoundException){
                Toast.makeText(context, "مشکلی به وجود آمد", Toast.LENGTH_SHORT).show()
            }
        }


        private fun downloadFile(position: Int) {
            val url = items[position].link
            val fileName = items[position].name

            val req = kDownloader.newRequestBuilder(url, dirPath, fileName)
                .build()
            kDownloader.enqueue(req, onStart = {
                Toast.makeText(context, "درحال دریافت فایل", Toast.LENGTH_SHORT).show()
            }, onCompleted = {
                Toast.makeText(context, "اتمام دریافت", Toast.LENGTH_SHORT).show()
                val downloadedFile = File(dirPath,fileName)
                shareFile(downloadedFile)

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
    fun updateData(newItem: ArrayList<AssetsApiResponseData>) {
        this.items.clear()
        this.items.addAll(newItem)
        notifyDataSetChanged()
    }
}
