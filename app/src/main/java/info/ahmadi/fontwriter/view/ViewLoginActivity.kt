package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.LoginOneFragment
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.ActivityLoginBinding
import javax.inject.Inject

@ActivityScoped
class ViewLoginActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = ActivityLoginBinding.inflate(LayoutInflater.from(context))
    @Inject
    lateinit var controller: Controller
    fun onStartUp(){
        controller.changeFragment(LoginOneFragment())
    }

}