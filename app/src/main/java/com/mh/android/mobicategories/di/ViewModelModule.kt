package com.mh.android.mobicategories.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mh.android.mobicategories.ui.fragment.FoodListViewModel
import com.mh.android.mobicategories.ui.main.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * View model class to provide android view model class instance
 * Created by @author Mubarak Hussain.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    internal abstract fun bindFoodListViewModel(listViewModel: FoodListViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}