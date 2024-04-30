package info.ahmadi.fontwriter.view

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.ActivityHelperBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@ActivityScoped
class ViewHelperActivity @Inject constructor(@ActivityContext context: Context):FrameLayout(context) {
    val binding = ActivityHelperBinding.inflate(LayoutInflater.from(context))
    @Inject
    lateinit var api:ApiInterface
    @Inject
    lateinit var controller : Controller
    fun onStartUp(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                api.getVideo().awaitResponse()
            }catch (_:Exception){
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
                    binding.player.visibility = View.VISIBLE
                    loadVideo(response.body()!!.videoLink)
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
    private fun loadVideo(link:String){
        val controller = MediaController(context)
        controller.setAnchorView(binding.player)
        binding.player.setMediaController(controller)
        binding.player.setVideoURI(Uri.parse(link))
        binding.player.setOnPreparedListener {
            it.start()
        }
        binding.player.setOnErrorListener { _, _, _ ->
            Toast.makeText(context, "لطفا اینترنت خود را چک کنید", Toast.LENGTH_SHORT).show()
            false
        }
    }
}