package info.ahmadi.fontwriter.presenter

import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.view.ViewSplashActivity
import javax.inject.Inject
@ActivityScoped
class PresenterSplashActivity @Inject constructor() {
    @Inject
    lateinit var view: ViewSplashActivity
    fun onCreate() {
        view.onStartUp()
    }
}