package com.mh.android.mobicategories.ui.main

import android.os.Bundle
import android.widget.ImageView
import com.mh.android.mobicategories.BuildConfig
import com.mh.android.mobicategories.R
import com.mh.android.mobicategories.extension.toast
import com.mh.android.mobicategories.model.Product
import com.mh.android.mobicategories.ui.fragment.FoodListFragment.Companion.PRODUCT_DATA
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_details.*

class FoodDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addLayout(R.layout.activity_food_details)
        val product = intent.getSerializableExtra(PRODUCT_DATA) as Product
        setHomeButtonEnabled(true)
        setBackButtonEnabled(true)
        setToolbarTitle(getString(R.string.details_activity_title))
        loadDetailsView(product)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadDetailsView(product: Product) {
        showLoader(true)
        val mContentView = findViewById<ImageView>(R.id.image)
        if (product.url.isNullOrEmpty()) {
            mContentView.context.toast(getString(R.string.went_wrong))
            finish()
        }

        product.description?.let {
            ivDesc.text = it
        }

        Picasso.with(this).load(BuildConfig.BASE_URL + product.url).fit().into(mContentView, object : Callback {
            override fun onSuccess() {
                showLoader(false)
            }

            override fun onError() {
                showLoader(false)
                mContentView.context.toast(getString(R.string.went_wrong))
                finish()
            }
        })
    }
}