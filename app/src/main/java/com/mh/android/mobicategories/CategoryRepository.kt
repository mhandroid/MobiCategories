package com.mh.android.mobicategories

import com.mh.android.mobicategories.api.ApiInterface
import com.mh.android.mobicategories.model.FoodCategories
import retrofit2.Response

/**
 * Created by mubarak.hussain on 06/02/21.
 */
class CategoryRepository(private val apiInterface: ApiInterface) {

    suspend fun getCategory(): Response<List<FoodCategories>> = apiInterface.getListOfCategories()
}