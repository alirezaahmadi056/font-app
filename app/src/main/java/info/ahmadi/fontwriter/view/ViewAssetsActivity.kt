package info.ahmadi.fontwriter.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TableLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import info.ahmadi.fontwriter.R
import info.ahmadi.fontwriter.api.ApiInterface
import info.ahmadi.fontwriter.controller.Controller
import info.ahmadi.fontwriter.customAd.AssetsAdapter
import info.ahmadi.fontwriter.customAd.ViewPagerAdapter
import info.ahmadi.fontwriter.databinding.ActivityAssetsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
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