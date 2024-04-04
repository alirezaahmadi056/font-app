package info.ahmadi.fontwriter.module

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.FragmentScoped
import info.ahmadi.fontwriter.controller.Controller

@Module
@InstallIn(ActivityComponent::class)
class ModuleActivity {
    @Provides
    fun provideFragmentManager(activity: FragmentActivity): FragmentManager = activity.supportFragmentManager
    @Provides
    fun provideController(activityComponent: Activity):Controller = activityComponent as Controller

}