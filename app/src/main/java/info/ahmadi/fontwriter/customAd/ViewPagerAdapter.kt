package info.ahmadi.fontwriter.customAd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import dagger.hilt.android.qualifiers.ActivityContext
import info.ahmadi.fontwriter.FreeAssetsFragment
import info.ahmadi.fontwriter.GoldAssetsFragment
import javax.inject.Inject


class ViewPagerAdapter @Inject constructor(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private var items:Array<Fragment> = arrayOf(GoldAssetsFragment(),FreeAssetsFragment())
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Fragment = items[position]
    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "ویژه ها"
            1 -> "پس زمینه ها"
            else -> ""
        }
    }
}