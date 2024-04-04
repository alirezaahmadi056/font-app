package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewFreeAssetsFragment
import javax.inject.Inject

class PresenterFreeAssetsFragment @Inject constructor() {
    @Inject
    lateinit var view : ViewFreeAssetsFragment
    fun onCreate() {
        view.onStartUp()
    }
}