package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewLoginActivity
import javax.inject.Inject

class PresenterLoginActivity @Inject constructor() {
    @Inject
    lateinit var view: ViewLoginActivity
    fun onCreate() {

        view.onStartUp()

    }
}