package com.mh.android.mobicategories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mh.android.mobicategories.api.ApiInterface
import com.mh.android.mobicategories.model.ApiResult
import com.mh.android.mobicategories.model.FoodCategories
import com.mh.android.mobicategories.model.Product
import com.mh.android.mobicategories.ui.fragment.FoodListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Created by mubarak.hussain on 07/02/21.
 */
@ExperimentalCoroutinesApi
class FoodListViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiEndPoint: ApiInterface
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var observer: Observer<ApiResult<List<FoodCategories>>>
    lateinit var repository: CategoryRepository
    private lateinit var viewModel: FoodListViewModel
    private var list: MutableList<FoodCategories> = mutableListOf()

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        repository = CategoryRepository(apiEndPoint)
        viewModel = FoodListViewModel(repository)
        genrateFoodCategory()
    }

    @Test
    fun getListOfProducts() {
        viewModel.getListOfProducts().observeForever(observer)
    }

    private fun genrateFoodCategory() {
        val product = Product(2, 1, "Food", "www.dummyurl.com", "Food desc", null)
        val foodCategories = FoodCategories(1, "test", "test desc", listOf(product))
        list.add(foodCategories)
    }

    @Test
    fun testNull() {
        runBlockingTest {
            `when`(repository.getCategory()).thenReturn(null)
        }
        val apiResult = viewModel.getListOfProducts()
        assertNotNull(apiResult)

        val liveDataValue = apiResult.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue is ApiResult.Error)
        assertEquals("Something went wrong please try again!!", (liveDataValue as ApiResult.Error).error)
    }

    @Test
    fun testApiFetchDataSuccess() {
        runBlockingTest {
            // Mock API response
            `when`(repository.getCategory()).thenReturn(Response.success(list))
        }
        val apiResult = viewModel.getListOfProducts()
        assertNotNull(apiResult)
        val liveDataValue = apiResult.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue is ApiResult.Success)
        assertNotNull((liveDataValue as ApiResult.Success).data)
    }

    @Test
    fun testApiFetchDataError() {
        runBlockingTest {
            // Mock API response
            `when`(repository.getCategory()).thenReturn(Response.error(403, ResponseBody.create(null, "Error while fetching data")))
        }
        val apiResult = viewModel.getListOfProducts()
        assertNotNull(apiResult)
        val liveDataValue = apiResult.value
        assertNotNull(liveDataValue)
        assertTrue(liveDataValue is ApiResult.Error)
        assertNotNull((liveDataValue as ApiResult.Error).error)
    }
}