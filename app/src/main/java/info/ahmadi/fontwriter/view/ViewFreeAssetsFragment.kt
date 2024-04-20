package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.AssetsAdapter
import info.ahmadi.fontwriter.databinding.FragmentFreeAssetsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@FragmentScoped
class ViewFreeAssetsFragment @Inject constructor(@ActivityContext context:Context) : FrameLayout(context){
    val binding = FragmentFreeAssetsBinding.inflate(LayoutInflater.from(context))
    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var adapter : AssetsAdapter

    @Inject
    lateinit var controller: Controller
    fun onStartUp() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                api.getAssets().awaitResponse()
            }catch (_:Exception){
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, customBinding = {
                        it.exit.text = "برگشت"
                    }, retry = {
                        onStartUp()
                    }, exit = {
                        controller.finishFromController()
                    })
                }
                return@launch
            }
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch{
                    binding.recycler.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager = StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.VERTICAL)
                    adapter.updateData(response.body()!!.assets.filter {
                        it.isDownload
                    })
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, customBinding = {
                        it.exit.text = "برگشت"
                    }, retry = {
                        onStartUp()
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }
}