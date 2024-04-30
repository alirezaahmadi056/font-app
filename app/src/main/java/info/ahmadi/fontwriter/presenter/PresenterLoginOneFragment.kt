package info.ahmadi.fontwriter.presenter

import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewLoginOneFragment
import javax.inject.Inject

class PresenterLoginOneFragment @Inject constructor() {
    @Inject
    lateinit var view: ViewLoginOneFragment
    fun onCreate() {
        view.onLoginClick()
        view.onRuleClick()
    }
}