package info.ahmadi.fontwriter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.ViewPagerAdapter
import info.ahmadi.fontwriter.presenter.PresenterAssetsActivity
import info.ahmadi.fontwriter.view.ViewAssetsActivity
import javax.inject.Inject

@AndroidEntryPoint
class AssetsActivity : AppCompatActivity() , Controller{
    @Inject
    lateinit var view:ViewAssetsActivity
    @Inject
    lateinit var presenter:PresenterAssetsActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.binding.root)
        presenter.onCreate()

    }
    override fun finishFromController() {
        finish()
    }
}