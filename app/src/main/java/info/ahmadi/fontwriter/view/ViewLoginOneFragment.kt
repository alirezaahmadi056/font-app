package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import info.ahmadi.fontwriter.LoginTwoFragment
import info.ahmadi.fontwriter.R
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
class ViewLoginOneFragment @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = FragmentLoginOneBinding.inflate(LayoutInflater.from(context))

    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var controller: Controller
    fun onLoginClick() {
        binding.login.setOnClickListener {
            if (binding.phone.text.toString().length == 11 && binding.phone.text.toString()
                    .startsWith("09")
            ) {
                if (binding.login.text == "ارسال کد") {
                    binding.login.text = "منتظر بمانید"
                    phone = binding.phone.text.toString()
                    requestApi()
                }
            } else {
                Toast.makeText(context, "شماره همراه شما تایید نشد", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                try {
                    api.loginApi(LoginApiRequest(binding.phone.text.toString())).awaitResponse()
                } catch (_: Exception) {
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.login.text = "ارسال کد"
                        controller.networkError(context, retry = {
                            requestApi()

                        }, exit = {
                            controller.finishFromController()
                        })
                    }
                    return@launch
                }
            if (response.code() == 200) {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.changeFragment(LoginTwoFragment())
                    binding.login.text = "ارسال کد"
                }

            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.login.text = "ارسال کد"
                    controller.networkError(context, retry = {
                        binding.login.text = "منتظر بمانید"
                        requestApi()
                    }, exit = {
                        controller.finishFromController()
                    })
                }
            }
        }
    }

    fun onRuleClick() {
        binding.rule.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setView(R.layout.dialog_rule)
                .show()

        }
    }

}