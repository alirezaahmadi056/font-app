package info.ahmadi.fontwriter.presenter

import androidx.fragment.app.FragmentManager
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewHomeActivity
import javax.inject.Inject

class PresenterHomeActivity @Inject constructor() {
    @Inject
    lateinit var view:ViewHomeActivity
    fun onCreate(controller: Controller? = null,fragmentManager: FragmentManager){
        controller?.let {
            view.onStartUp(it)
        }
        view.changeTextColor(fragmentManager)
        view.changeGravity()
        view.changeTextSize()
        view.clearText()
        view.onAssetsClick()
        view.saveText()
    }
}