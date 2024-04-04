package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewAssetsActivity
import javax.inject.Inject

class PresenterAssetsActivity @Inject constructor() {
    @Inject
    lateinit var view : ViewAssetsActivity
    fun onCreate(){
        view.onStartUp()
    }
}