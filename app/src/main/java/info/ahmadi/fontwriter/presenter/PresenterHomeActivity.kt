package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.view.ViewHomeActivity
import javax.inject.Inject

class PresenterHomeActivity @Inject constructor() {
    @Inject
    lateinit var view:ViewHomeActivity
    fun onCreate(){
        view.onStartUp()
        view.changeTextColor()
        view.changeGravity()
        view.changeTextSize()
        view.clearText()
        view.onAssetsClick()
        view.saveText()
        view.onContactClick()
        view.onHelperClick()
        view.changeFontButtonClick()
    }
}