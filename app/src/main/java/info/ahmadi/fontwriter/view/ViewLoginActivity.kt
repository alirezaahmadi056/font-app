package info.ahmadi.fontwriter.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.HomeActivity
import info.ahmadi.fontwriter.LoginOneFragment
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.ActivityLoginBinding
import info.ahmadi.fontwriter.model.CheckCodeApiRequest
import info.ahmadi.fontwriter.model.LoginApiRequest
import info.ahmadi.fontwriter.model.availableCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@ActivityScoped
class ViewLoginActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = ActivityLoginBinding.inflate(LayoutInflater.from(context))

    fun onStartUp(controller: Controller){
        controller.changeFragment(LoginOneFragment())
    }

}