package com.mh.android.mobicategories.api

import com.mh.android.mobicategories.model.FoodCategories
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface of api resource
 * Created by @author Mubarak Hussain.
 */
interface ApiInterface {
    /**
     * Interface method to of get request for food category list
     * @return
     */
    @GET("/")
    suspend fun getListOfCategories(): Response<List<FoodCategories>>

}