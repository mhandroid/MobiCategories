package com.mh.android.mobicategories.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mh.android.mobicategories.MobiApplication
import com.mh.android.mobicategories.R
import com.mh.android.mobicategories.adapter.SectionsPagerAdapter
import com.mh.android.mobicategories.model.ApiResult
import com.mh.android.mobicategories.model.FoodCategories
import com.mh.android.mobicategories.ui.fragment.FoodListViewModel
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    private lateinit var homeViewModel: FoodListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        configureDagger()
        loadData()
    }

    private fun initViews() {
        addLayout(R.layout.activity_main)
        addTabBar(R.layout.tab_bar)
        setHomeIcon(R.mipmap.ic_launcher_round)
        setHomeButtonEnabled(true)
    }

    private fun loadData() {
        showLoader(true)
        homeViewModel = viewModelFactory.create(FoodListViewModel::class.java)
        homeViewModel.getListOfProducts().observe(this, { apiResult ->
            showLoader(false)
            when (apiResult) {
                is ApiResult.Success -> initPager(apiResult.data)
                is ApiResult.Error -> showErrorText(apiResult.error)
                else -> Log.i("HomeActivity", "Unknown Error")
            }
        })
    }

    /**
     * Method to init dagger
     */
    private fun configureDagger() {
        (application as MobiApplication).getAppComponent().inject(this)
    }

    private fun initPager(categories: List<FoodCategories>?) {
        categories?.let {
            val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, it)
            val viewPager: ViewPager = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            tabs.setupWithViewPager(viewPager)
        } ?: showErrorText(resources.getString(R.string.no_data_available))
    }
}