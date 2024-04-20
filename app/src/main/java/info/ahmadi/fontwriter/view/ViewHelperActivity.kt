package info.ahmadi.fontwriter.view

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
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
    private val exoPlayer = ExoPlayer.Builder(context).build()
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
                    binding.title.text = response.body()!!.name
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
        val media = MediaItem.Builder()
            .setUri(Uri.parse(link))
            .build()
        exoPlayer.prepare()
        exoPlayer.setMediaItem(media)
        binding.player.player = exoPlayer
        exoPlayer.playWhenReady = true
        exoPlayer.addListener(object :Player.Listener{
            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(context, "انترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show()
            }
        })
    }
}