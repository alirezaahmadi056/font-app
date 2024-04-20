package info.ahmadi.fontwriter.presenter

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import info.ahmadi.fontwriter.view.ViewHelperActivity
import info.ahmadi.fontwriter.view.ViewHomeActivity
import javax.inject.Inject

class PresenterHelperActivity @Inject constructor() {
    @Inject
    lateinit var view :ViewHelperActivity
    fun onCreate(){
        view.onStartUp()
    }
}