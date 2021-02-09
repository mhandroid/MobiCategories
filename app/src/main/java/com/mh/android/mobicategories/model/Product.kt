package com.mh.android.mobicategories.model

import java.io.Serializable

data class Product(
	val id: Int, val categoryId: Int, val name: String?, val url: String?, val description: String?, val salePrice: SalePrice?):Serializable