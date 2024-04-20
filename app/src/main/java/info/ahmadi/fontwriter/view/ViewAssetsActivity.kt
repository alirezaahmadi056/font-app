package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.customAd.ViewPagerAdapter
import info.ahmadi.fontwriter.databinding.ActivityAssetsBinding
import javax.inject.Inject

@ActivityScoped
class ViewAssetsActivity @Inject constructor(@ActivityContext context: Context) :
    FrameLayout(context) {
    val binding = ActivityAssetsBinding.inflate(LayoutInflater.from(context))
    @Inject
    lateinit var adapter: ViewPagerAdapter
    fun onStartUp() {
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}