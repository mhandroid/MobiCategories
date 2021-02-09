package com.mh.android.mobicategories.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mh.android.mobicategories.R
import com.mh.android.mobicategories.adapter.FoodCategoryAdapter
import com.mh.android.mobicategories.model.Product
import com.mh.android.mobicategories.ui.main.FoodDetailsActivity

class FoodListFragment : Fragment(), FoodCategoryAdapter.ListItemCLickListener {
    private var adapter: FoodCategoryAdapter? = null
    private var columnCount = 1
    lateinit var products: List<Product>

    companion object {
        const val PRODUCT_DATA: String = "PRODUCT_DATA"
        fun newInstance(products: List<Product>): FoodListFragment {
            val fragment = FoodListFragment()
            fragment.products = products
            fragment.retainInstance = true
            return fragment
        }
    }

    lateinit var progressBar: ProgressBar
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        // Set the adapter
        initializeView(view)
        return view
    }

    /**
     * Method to initialize view
     */
    private fun initializeView(root: View) {
        progressBar = root.findViewById(R.id.btnProgress)

        mRecyclerView = root.findViewById(R.id.list)
        with(mRecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        val linearLayoutManager = LinearLayoutManager(root.context)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.setHasFixedSize(true)
        adapter = FoodCategoryAdapter(root.context, products, this)
        mRecyclerView.adapter = adapter
    }

    override fun onItemClick(product: Product) {
        startActivity(Intent(activity, FoodDetailsActivity::class.java).apply {
            this.putExtra(PRODUCT_DATA, product)
        })
    }
}