package info.ahmadi.fontwriter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterHomeActivity
import info.ahmadi.fontwriter.view.ViewHomeActivity
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() , Controller {
    @Inject
    lateinit var view:ViewHomeActivity
    @Inject
    lateinit var presenter:PresenterHomeActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.binding.root)
        presenter.onCreate(this,supportFragmentManager)
    }

    override fun finishFromController() {
        finish()
    }


}