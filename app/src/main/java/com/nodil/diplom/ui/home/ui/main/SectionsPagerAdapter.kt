package com.nodil.diplom.ui.home.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nodil.diplom.ui.home.map.MapsFragment
import com.nodil.diplom.ui.home.status.StatusFragment
import com.nodil.diplom.ui.home.task.TaskFragment

private val TAB_TITLES = arrayOf(
    "Статус",
    "Работа",
    "Карта"
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StatusFragment.newInstance()
            1 -> TaskFragment.newInstance()
            else -> MapsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}