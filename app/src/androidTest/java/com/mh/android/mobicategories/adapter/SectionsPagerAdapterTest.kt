package com.mh.android.mobicategories.adapter

import androidx.fragment.app.FragmentManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mh.android.mobicategories.model.FoodCategories
import com.mh.android.mobicategories.model.Product
import com.mh.android.mobicategories.ui.fragment.FoodListFragment
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Created by mubarak.hussain on 07/02/21.
 */
@RunWith(AndroidJUnit4::class)
class SectionsPagerAdapterTest {

    lateinit var fragmentManager: FragmentManager

    @Before
    fun setup() {
        fragmentManager = Mockito.mock(FragmentManager::class.java)
    }

    @Test
    fun test_AdapterInit() {
        val sectionPagerAdapter = SectionsPagerAdapter(fragmentManager, emptyList())
        assertNotNull("Adapter should not be null", sectionPagerAdapter)
    }

    @Test
    fun test_getPageTitle() {
        val dummyData = generateListOfCategory()
        val sectionPagerAdapter = SectionsPagerAdapter(fragmentManager, dummyData)
        assertNotNull("Adapter should not be null", sectionPagerAdapter)
        assertEquals(dummyData[0].name, sectionPagerAdapter.getPageTitle(0))
        assertEquals(dummyData[1].name, sectionPagerAdapter.getPageTitle(1))
    }

    @Test
    fun test_getItem() {
        val sectionPagerAdapter = SectionsPagerAdapter(fragmentManager, generateListOfCategory())
        assertNotNull("Adapter should not be null", sectionPagerAdapter)
        assertTrue(sectionPagerAdapter.getItem(0) is FoodListFragment)
        assertTrue(sectionPagerAdapter.getItem(1) is FoodListFragment)
    }

    @Test
    fun test_getCount() {
        val sectionPagerAdapter = SectionsPagerAdapter(fragmentManager, emptyList())
        assertNotNull(sectionPagerAdapter)
        //initial state
        val initialExpected = 0
        val initialActual: Int = sectionPagerAdapter.count
        assertEquals(initialExpected.toLong(), initialActual.toLong())

        //initial state 2
        val sectionPagerAdapter2 = SectionsPagerAdapter(fragmentManager, generateListOfCategory())
        assertEquals(2, sectionPagerAdapter2.count)
    }

    private fun generateListOfCategory(): List<FoodCategories> {
        val productList = mutableListOf<FoodCategories>()
        productList.add(FoodCategories(2, "name2", "Desc", listOf(Product(1, 2, "food", "url", "desc", null))))
        productList.add(FoodCategories(1, "name", "Desc", listOf(Product(2, 3, "drink", "url", "desc", null))))
        return productList
    }
}