package info.ahmadi.fontwriter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterSplashActivity
import info.ahmadi.fontwriter.view.ViewSplashActivity
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity(), Controller {

    @Inject
    lateinit var view: ViewSplashActivity

    @Inject
    lateinit var presenter: PresenterSplashActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.binding.root)
        presenter.onCreate()
    }

    override fun startActivityFromController(context: Context?, intent: Intent) {
        finish()
        startActivity(intent)
    }

}
