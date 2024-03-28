package info.ahmadi.fontwriter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.presenter.PresenterLoginActivity
import info.ahmadi.fontwriter.view.ViewLoginActivity
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() , Controller{
    @Inject
    lateinit var view:ViewLoginActivity
    @Inject
    lateinit var presenter:PresenterLoginActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.binding.root)
        presenter.onCreate(this)
    }

    override fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit()
    }
}