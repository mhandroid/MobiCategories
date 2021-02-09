package com.mh.android.mobicategories.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mh.android.mobicategories.exception.NoNetworkException
import com.mh.android.mobicategories.CategoryRepository
import com.mh.android.mobicategories.model.ApiResult
import com.mh.android.mobicategories.model.FoodCategories
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FoodListViewModel @Inject constructor(private val repository: CategoryRepository) : ViewModel() {

    private val products = MutableLiveData<ApiResult<List<FoodCategories>>>()
    private val TAG: String = FoodListViewModel::class.toString()

    fun getListOfProducts(): LiveData<ApiResult<List<FoodCategories>>> {
        viewModelScope.launch {
            try {
                val result = repository.getCategory()
                loadData(result)
            } catch (e: Exception) {
                handelError(e)
            }
        }
        return products
    }

    private fun loadData(response: Response<List<FoodCategories>>) {
        Log.d(TAG, "getListOfProduct received result called $response")
        if (response.isSuccessful && response.body() != null) {
            products.value = ApiResult.Success(response.body())
        } else {
            if (response.code() in (500..599)) {
                // try again if there is a server error
                Log.d(TAG, "Retry response.message:${response.message()}, ${response.errorBody().toString()}")
                products.value = ApiResult.Retry(response.message())
            }
            products.value = ApiResult.Error(response.message())
        }
    }

    private fun handelError(ex: Exception) {
        Log.i("MUB", "Network error occurs ${ex.localizedMessage}")
        when (ex) {
            is IOException -> {
                products.value = ApiResult.Error("Network error!!!")
            }
            is NoNetworkException -> {
                products.value = ApiResult.Error("No internet connection!!!")
            }
            else -> {
                products.value = ApiResult.Error("Something went wrong please try again!!")
            }
        }
    }
}