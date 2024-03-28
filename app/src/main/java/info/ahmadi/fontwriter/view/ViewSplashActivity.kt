package info.ahmadi.fontwriter.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.FrameLayout
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.HomeActivity
import info.ahmadi.fontwriter.LoginActivity
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.ActivitySplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@ActivityScoped
class ViewSplashActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = ActivitySplashBinding.inflate(LayoutInflater.from(context))
    fun onStartUp(controller: Controller) {
        val goToHome = flow {
            delay(3000L)
            emit(true)
        }
        val pref = context.getSharedPreferences("font",Context.MODE_PRIVATE)
        val login = pref.getBoolean("login",false)
        CoroutineScope(Dispatchers.Main).launch {
            goToHome.collect {
                if (it) {
                    controller.startActivityFromController(
                        intent = Intent(
                            context,
                            if (login) HomeActivity::class.java else HomeActivity::class.java
                        )
                    )
                }
            }
        }
    }
}