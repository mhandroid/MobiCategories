package com.mh.android.mobicategories.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mh.android.mobicategories.model.FoodCategories
import com.mh.android.mobicategories.ui.fragment.FoodListFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, private val foodCategories: List<FoodCategories>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        foodCategories[position].products?.let {
             return FoodListFragment.newInstance(it)
        }
        return Fragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return foodCategories[position].name
    }

    override fun getCount(): Int {
        return foodCategories.size
    }
}