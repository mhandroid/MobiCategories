package com.mh.android.mobicategories.adapter

import android.content.Context
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mh.android.mobicategories.R
import com.mh.android.mobicategories.model.Product
import com.mh.android.mobicategories.model.SalePrice
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by mubarak.hussain on 07/02/21.
 */
@RunWith(AndroidJUnit4::class)
class FoodCategoryAdapterTest {

    @Test
    fun testAdapterInit() {
        // Context of the app under test.
        val appContext: Context = ApplicationProvider.getApplicationContext()
        var foodCategoryAdapter = FoodCategoryAdapter(appContext, emptyList(), ItemClickListener())
        assertNotNull("Adapter should not be null", foodCategoryAdapter)

        foodCategoryAdapter = FoodCategoryAdapter(appContext, emptyList(), null)
        assertNotNull("Adapter object should not be null", foodCategoryAdapter)
    }

    @Test
    fun test_onBindViewHolder() {
        val appContext: Context = InstrumentationRegistry.getInstrumentation().context
        val foodCategoryAdapter = FoodCategoryAdapter(appContext, generateListOfProduct(), ItemClickListener())
        val recyclerView = RecyclerView(appContext)
        recyclerView.layoutManager = LinearLayoutManager(appContext)
        recyclerView.adapter = foodCategoryAdapter
        assertNotNull(recyclerView.adapter)
        assertEquals(2, recyclerView.adapter?.itemCount)
    }

    @Test
    fun test_onCreateViewHolder() {
        val appContext: Context = ApplicationProvider.getApplicationContext()
        val foodCategoryAdapter = FoodCategoryAdapter(appContext, generateListOfProduct(), ItemClickListener())
        val parent = FrameLayout(appContext)

        //child view holder
        val viewHolder: RecyclerView.ViewHolder = foodCategoryAdapter.onCreateViewHolder(parent, 0)
        assertNotNull("View holder should not be null", viewHolder)
        assertNotNull("Item view should not be null", viewHolder.itemView)
        assertNotNull("Image view should not be null", viewHolder.itemView.findViewById(R.id.ivProduct))
        assertNotNull("Text view should not be null", viewHolder.itemView.findViewById(R.id.tvTitle))
    }
    @Test
    fun test_getItemCount() {
        val appContext: Context = InstrumentationRegistry.getInstrumentation().context
        val itemClickListener = ItemClickListener()
        val foodCategoryAdapter = FoodCategoryAdapter(appContext, emptyList(), itemClickListener)

        //initial state
        val initialExpected = 0
        val initialActual: Int = foodCategoryAdapter.itemCount
        assertEquals(initialExpected.toLong(), initialActual.toLong())

        //initial state 2
        val foodCategoryAdapter2 = FoodCategoryAdapter(appContext, generateListOfProduct(), itemClickListener)
        assertEquals(2, foodCategoryAdapter2.itemCount)
    }

    private fun generateListOfProduct(): List<Product> {
        val productList = mutableListOf<Product>()
        productList.add(Product(2, 3, "name2", "url2", "desc2", SalePrice(2.4, "INR")))
        productList.add(Product(1, 2, "name", "url", "desc", SalePrice(2.4, "DEK")))
        return productList
    }

    internal class ItemClickListener : FoodCategoryAdapter.ListItemCLickListener {
        var product: Product? = null
        override fun onItemClick(product: Product) {
            this.product = product
        }
    }
}