package com.mh.android.mobicategories.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mh.android.mobicategories.R

abstract class BaseActivity : AppCompatActivity() {
    private val TAG = BaseActivity::class.java.simpleName
    private lateinit var txtError: TextView
    protected lateinit var mToolbar: Toolbar
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_layout)
        initViews()
        setupActionBar()
    }

    private fun initViews() {
        mToolbar = findViewById(R.id.toolbar)
        mProgressBar = findViewById(R.id.btnProgress)
        txtError = findViewById(R.id.textError)
    }

    protected fun addTabBar(layoutId: Int) {
        val mainContainer = findViewById<FrameLayout>(R.id.app_bar_container)
        mainContainer.addView(layoutInflater.inflate(layoutId, null))
    }

    protected open fun showErrorText(msg: String?) {
        txtError.text = msg
        txtError.visibility = View.VISIBLE
    }

    protected open fun hideErrorText() {
        txtError.visibility = View.GONE
    }

    protected open fun setHomeIcon(resId: Int) {
        mToolbar.setNavigationIcon(resId)
    }

    protected fun showLoader(show: Boolean) {
        if (show) mProgressBar.visibility = View.VISIBLE
        else mProgressBar.visibility = View.GONE
    }

    /**
     * Method to enable disable home button
     *
     * @param isEnabled
     */
    protected open fun setHomeButtonEnabled(isEnabled: Boolean) {
        supportActionBar?.setDisplayShowHomeEnabled(isEnabled)
    }

    protected open fun setBackButtonEnabled(isEnabled: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
    }

    /**
     * Method to setup action bar
     */
    protected open fun setupActionBar() {
        setSupportActionBar(mToolbar)
    }

    /**
     * Method to set toolbar title
     *
     * @param title
     */
    protected open fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    /**
     * Method to add layout with extended activity
     *
     * @param layoutId
     */
    protected open fun addLayout(layoutId: Int) {
        try {
            val mainContainer = findViewById<FrameLayout>(R.id.main_container_layout)
            mainContainer.addView(layoutInflater.inflate(layoutId, null))
        } catch (exception: Exception) {
            Log.e(TAG, exception.message, exception)
        }
    }
}