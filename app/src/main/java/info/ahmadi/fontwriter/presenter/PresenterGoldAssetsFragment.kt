package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewGoldAssetsFragment
import javax.inject.Inject

class PresenterGoldAssetsFragment @Inject constructor() {
    @Inject
    lateinit var view : ViewGoldAssetsFragment
    fun onCreate() {
        view.onStartUp()


    }
}