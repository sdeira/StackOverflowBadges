package com.example.stackoverflowbadges.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.stackoverflowbadges.model.Filters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import java.com.example.stackoverflowbadges.TestCoroutineRule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RedditDataBaseTest {

    private lateinit var filterDao: FiltersDao
    private lateinit var db: StackOverflowDataBase

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, StackOverflowDataBase
            ::class.java).build()
        filterDao = db.filtersDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun setFilterMaShouldReturnTheFilterWithSameMax() {
        testCoroutineRule.runBlockingTest {
            val rank = "gold"

            filterDao.insert(Filters(1, "asc", "silver", "silver"))
            filterDao.filterMax(rank)

            val filter = filterDao.getFilter()
            Assert.assertEquals(filter?.max, rank)
        }
    }

    @Test
    fun setFilterMinShouldReturnTheFilterWithSameMin() {
        testCoroutineRule.runBlockingTest {
            val rank = "gold"

            filterDao.insert(Filters(1, "asc", "silver", "silver"))
            filterDao.filterMin(rank)

            val filter = filterDao.getFilter()
            Assert.assertEquals(filter?.min, rank)
        }
    }

    @Test
    fun setFilterOrderShouldReturnTheFilterWithSameOrder() {
        testCoroutineRule.runBlockingTest {
            val order = "desc"

            filterDao.insert(Filters(1, "asc", "silver", "silver"))
            filterDao.filterOrder(order)

            val filter = filterDao.getFilter()
            Assert.assertEquals(filter?.order, order)
        }
    }

}