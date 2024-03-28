package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import info.ahmadi.fontwriter.LoginTwoFragment
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.FragmentLoginOneBinding
import info.ahmadi.fontwriter.model.LoginApiRequest
import info.ahmadi.fontwriter.model.phone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@FragmentScoped
class ViewLoginOneFragment @Inject constructor(@ActivityContext context: Context) : FrameLayout(context)  {
    val binding = FragmentLoginOneBinding.inflate(LayoutInflater.from(context))
    @Inject
    lateinit var api: ApiInterface
    fun onLoginClick(controller: Controller) {
        binding.login.setOnClickListener {
            if (binding.phone.text.toString().length == 11 && binding.phone.text.toString()
                    .startsWith("09")
            ) {
                if (binding.login.text == "ارسال کد") {
                    binding.login.text = "منتظر بمانید"
                    phone = binding.phone.text.toString()
                    requestApi(controller)
                }
            } else {
                Toast.makeText(context, "شماره همراه شما تایید نشد", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun requestApi(controller: Controller) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                api.loginApi(LoginApiRequest(binding.phone.text.toString())).awaitResponse()
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.login.text = "ارسال کد"
                    controller.changeFragment(LoginTwoFragment())
                }

            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.login.text = "ارسال کد"
                    controller.networkError(context, retry = {
                        requestApi(controller)
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }

}