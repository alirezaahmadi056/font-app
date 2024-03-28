package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.AssetsAdapter
import info.ahmadi.fontwriter.databinding.ActivityAssetsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@ActivityScoped
class ViewAssetsActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = ActivityAssetsBinding.inflate(LayoutInflater.from(context))

    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var adapter : AssetsAdapter
    fun onStartUp(controller: Controller) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getAssets().awaitResponse()
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch{
                    binding.recycler.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
                    adapter.updateData(response.body()!!.assets)
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, customBinding = {
                        it.exit.text = "برگشت"
                    }, retry = {
                        onStartUp(controller)
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }
}