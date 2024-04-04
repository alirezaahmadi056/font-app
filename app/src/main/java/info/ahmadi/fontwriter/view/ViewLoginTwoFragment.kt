package info.ahmadi.fontwriter.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import info.ahmadi.fontwriter.HomeActivity
import info.ahmadi.fontwriter.LoginOneFragment
import info.ahmadi.fontwriter.R
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.databinding.FragmentLoginTwoBinding
import info.ahmadi.fontwriter.model.CheckCodeApiRequest
import info.ahmadi.fontwriter.model.LoginApiRequest
import info.ahmadi.fontwriter.model.availableCode
import info.ahmadi.fontwriter.model.phone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import javax.inject.Inject

@FragmentScoped
class ViewLoginTwoFragment @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = FragmentLoginTwoBinding.inflate(LayoutInflater.from(context))

    @Inject
    lateinit var api: ApiInterface

    @Inject
    lateinit var controller: Controller

    fun onStartUp(){
        binding.title.text = "کد ارسالی به شماره ی ${phone} را وارد نمایید "
    }

    fun startTimer() {
        availableCode = true
        var time = 60
        val flow = flow<String> {
            while (time > 0) {
                time -= 1
                delay(1000L)
                emit(time.toString())


            }
            binding.retry.text = "ارسال مجدد"
            availableCode = false
        }

        CoroutineScope(Dispatchers.Main).launch {
            flow.collect { remainingTime ->
                binding.retry.text = remainingTime
            }
        }
    }

    fun focusEditText() {
        var userCode = ""
        var c1 = ""
        var c2 = ""
        var c3 = ""
        var c4 = ""
        var c5 = ""
        var c6 = ""
        binding.editText1.doOnTextChanged { text, _, _, _ ->
            binding.editText2.requestFocus()
            c1 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode)

        }
        binding.editText2.doOnTextChanged { text, _, _, _ ->
            binding.editText3.requestFocus()
            c2 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode)
        }
        binding.editText3.doOnTextChanged { text, _, _, _ ->
            binding.editText4.requestFocus()
            c3 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode )
        }
        binding.editText4.doOnTextChanged { text, _, _, _ ->
            binding.editText5.requestFocus()
            c4 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode )
        }
        binding.editText5.doOnTextChanged { text, _, _, _ ->
            binding.editText6.requestFocus()
            c5 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode )

        }
        binding.editText6.doOnTextChanged { text, _, _, _ ->
            c6 = text.toString()
            userCode = c1 + c2 + c3 + c4 + c5 + c6
            checkCode(userCode )
        }
    }

    fun onChangeNumber() {
        binding.changeNumber.setOnClickListener {
            controller.changeFragment(LoginOneFragment())
        }
    }

    fun onResendCode() {
        binding.retry.setOnClickListener {
            if (binding.retry.text == "ارسال مجدد"){
                availableCode = true
                startTimer()
                resendCode()
            }
        }
    }

    private fun resendCode() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                api.loginApi(LoginApiRequest(phone)).awaitResponse()
            }catch (_:Exception){
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, customBinding = {
                        it.exit.text = "برگشت"
                    }, retry = {
                        resendCode()
                    }, exit = {
                        controller.changeFragment(LoginOneFragment())
                    })
                }
                return@launch
            }
            if (response.code() != 200) {
                CoroutineScope(Dispatchers.Main).launch {
                    controller.networkError(context, customBinding = {
                        it.exit.text = "برگشت"
                    }, retry = {
                        resendCode()
                    }, exit = {
                        controller.changeFragment(LoginOneFragment())
                    })
                }
            }
        }
    }

    private fun checkCode(userCode: String) {
        if (userCode.length == 6) {
            if (availableCode) {
                CoroutineScope(Dispatchers.IO).launch {
                    val response =
                        api.checkCode(CheckCodeApiRequest(phone, userCode))
                            .awaitResponse()
                    if (response.code() == 200) {
                        val pref = context.getSharedPreferences("font",Context.MODE_PRIVATE)
                        pref.edit().putBoolean("login",true).apply()
                        CoroutineScope(Dispatchers.Main).launch {
                            controller.startActivityFromController(
                                intent = Intent(
                                    context,
                                    HomeActivity::class.java
                                )
                            )
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.editText1.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.editText2.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.editText3.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.editText4.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.editText5.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.editText6.background =
                                ContextCompat.getDrawable(context, R.drawable.background_error)
                            binding.error.visibility = View.VISIBLE
                            controller.networkError(context, customBinding = {
                                it.exit.text = "بستن"
                            }, retry = {
                                checkCode(userCode)
                            }
                            )
                        }
                    }
                }
            } else {
                Toast.makeText(context, "تاریخ کد شما منقضی شده است", Toast.LENGTH_SHORT).show()
            }
        }

    }
}