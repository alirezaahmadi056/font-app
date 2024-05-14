package info.ahmadi.fontwriter.customAd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import info.ahmadi.fontwriter.FreeAssetsFragment
import info.ahmadi.fontwriter.GoldAssetsFragment
import javax.inject.Inject


class ViewPagerAdapter @Inject constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    private var items:Array<Fragment> = arrayOf(GoldAssetsFragment(),FreeAssetsFragment())
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}