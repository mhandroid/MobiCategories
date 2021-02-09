package com.mh.android.mobicategories.model

import java.io.Serializable

data class Category(val foodCategories: List<FoodCategories>) : Serializable
data class FoodCategories(val id: Int, val name: String, val description: String, val products: List<Product>?) : Serializable