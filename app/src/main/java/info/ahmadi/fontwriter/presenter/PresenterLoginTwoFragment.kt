package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewLoginTwoFragment
import javax.inject.Inject

class PresenterLoginTwoFragment @Inject constructor(){
    @Inject
    lateinit var view:ViewLoginTwoFragment
    fun onCreate(){
        view.startTimer()
        view.onStartUp()

        view.focusEditText()
        view.onResendCode()
        view.onChangeNumber()
    }
}