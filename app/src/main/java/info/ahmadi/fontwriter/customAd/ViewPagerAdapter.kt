package info.ahmadi.fontwriter.customAd

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
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