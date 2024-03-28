package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewLoginTwoFragment
import javax.inject.Inject

class PresenterLoginTwoFragment @Inject constructor(){
    @Inject
    lateinit var view:ViewLoginTwoFragment
    fun onCreate(controller: Controller? = null){
        view.startTimer()
        view.onStartUp()
        controller?.let {
            view.focusEditText(controller)
            view.onResendCode(controller)
            view.onChangeNumber(controller)
        }
    }
}