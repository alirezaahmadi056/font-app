package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.material.tabs.TabLayoutMediator
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

    private val tabText = arrayOf(
        "استیکرها",
        "پس زمینه ها"
    )
    fun onStartUp() {
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab , position ->
            tab.text = tabText[position]
        }.attach()
    }
}