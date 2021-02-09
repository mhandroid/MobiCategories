package com.mh.android.mobicategories.di

import com.mh.android.mobicategories.ui.main.HomeActivity
import com.mh.android.mobicategories.MobiApplication
import dagger.Component
import javax.inject.Singleton

/**
 * Created by @author Mubarak Hussain.
 */
@Singleton
@Component(modules = [ViewModelModule::class, NetModule::class, AppModule::class])
interface MobiAppComponent {
    fun inject(activity: HomeActivity)
    fun inject(app: MobiApplication)
}