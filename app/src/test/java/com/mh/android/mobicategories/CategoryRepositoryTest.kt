package com.mh.android.mobicategories

import com.mh.android.mobicategories.api.ApiInterface
import com.mh.android.mobicategories.model.FoodCategories
import com.mh.android.mobicategories.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.io.IOException
import java.util.*

/**
 * Created by mubarak.hussain on 07/02/21.
 */
class CategoryRepositoryTest {

    private lateinit var apiInterface: ApiInterface
    private var list: MutableList<FoodCategories>? = null

    @Before
    fun setUp() {
        apiInterface = Mockito.mock(ApiInterface::class.java)
        list = ArrayList<FoodCategories>()
        genrateFoodCategory()
    }

    private fun genrateFoodCategory() {
        val product = Product(2, 1, "Food", "www.dummyurl.com", "Food desc", null)
        val foodCategories = FoodCategories(1, "test", "test desc", listOf(product))
        list?.add(foodCategories)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when repository return success response`() {
        val userRepository = CategoryRepository(apiInterface)
        Assert.assertNotNull("No null", userRepository)
        runBlockingTest {
            Mockito.`when`(userRepository.getCategory()).thenReturn(Response.success(list))

            try {
                val response: Response<List<FoodCategories>> = userRepository.getCategory()
                Assert.assertEquals(200, response.code().toLong())
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNotNull(response.body())
                Assert.assertEquals(1, response.body()!!.size.toLong())

                val foodCategories: List<FoodCategories> = response.body() as List<FoodCategories>

                Assert.assertEquals("Food category id should be", 1, foodCategories[0].id)
                Assert.assertEquals("Food category should be", "test", foodCategories[0].name)
                Assert.assertEquals("Food category desc should be", "test desc", foodCategories[0].description)
                verifyProduct(foodCategories[0].products)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when repository return success response with null data`() {
        val userRepository = CategoryRepository(apiInterface)
        Assert.assertNotNull("No null", userRepository)
        runBlockingTest {
            Mockito.`when`(userRepository.getCategory()).thenReturn(Response.success(null))

            try {
                val response: Response<List<FoodCategories>> = userRepository.getCategory()
                Assert.assertEquals(200, response.code().toLong())
                Assert.assertTrue(response.isSuccessful)
                Assert.assertNull(response.body())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when repository return error response`() {
        val userRepository = CategoryRepository(apiInterface)
        Assert.assertNotNull("No null", userRepository)
        runBlockingTest {
            Mockito.`when`(userRepository.getCategory()).thenReturn(Response.error(403, ResponseBody.create(null, "Error while fetching data")))

            try {
                val response: Response<List<FoodCategories>> = userRepository.getCategory()
                Assert.assertEquals(403, response.code().toLong())
                Assert.assertFalse(response.isSuccessful)
                Assert.assertNull(response.body())
                val errorBody = response.errorBody()
                Assert.assertNotNull(errorBody)

                Assert.assertEquals("Error while fetching data", errorBody?.string().toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun verifyProduct(product: List<Product>?) {
        product?.let {
            Assert.assertEquals("Product id should be", 2, product[0].id)
            Assert.assertEquals("Product should be", "Food", product[0].name)
            Assert.assertEquals("Product desc should be", "Food desc", product[0].description)
            Assert.assertEquals("Product url should be", "www.dummyurl.com", product[0].url)
            Assert.assertNull("Product sale price should be", product[0].salePrice)
        }
    }
}
