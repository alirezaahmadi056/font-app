package info.ahmadi.fontwriter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterHelperActivity
import info.ahmadi.fontwriter.view.ViewHelperActivity
import javax.inject.Inject

@AndroidEntryPoint
class HelperActivity : AppCompatActivity(), Controller {
    @Inject
    lateinit var view:ViewHelperActivity
    @Inject
    lateinit var presenter:PresenterHelperActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.binding.root)
        presenter.onCreate()
    }
}