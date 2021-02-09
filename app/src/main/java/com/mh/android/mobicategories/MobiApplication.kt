package com.mh.android.mobicategories

import android.app.Application
import com.mh.android.mobicategories.di.AppModule
import com.mh.android.mobicategories.di.DaggerMobiAppComponent
import com.mh.android.mobicategories.di.MobiAppComponent

/**
 * Created by mubarak.hussain on 07/02/21.
 */
class MobiApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    /**
     * Initialize the dagger
     */
    private fun initDagger() {
        mobiAppComponent = DaggerMobiAppComponent.builder().appModule(AppModule(this)).build()
    }

    lateinit var mobiAppComponent: MobiAppComponent

    /**
     * Getter for  App component
     * @return
     */
    fun getAppComponent(): MobiAppComponent = mobiAppComponent
}