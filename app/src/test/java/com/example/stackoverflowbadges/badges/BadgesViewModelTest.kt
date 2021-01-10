package com.example.stackoverflowbadges.badges

import android.os.Build
import androidx.paging.ExperimentalPagingApi
import java.com.example.stackoverflowbadges.TestCoroutineRule
import com.example.stackoverflowbadges.data.BadgesRepository
import com.example.stackoverflowbadges.ui.badges.BadgesViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stackoverflowbadges.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class BadgesViewModelTest {
    lateinit var badgesViewModel: BadgesViewModel

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: BadgesRepository

    lateinit var spyRepository: BadgesRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        spyRepository = Mockito.spy(repository)
        badgesViewModel = BadgesViewModel(spyRepository)
    }

    @Test
    fun `test getting badges should call repository to get badges`() =
        testCoroutineRule.runBlockingTest {

            badgesViewModel.getBadges()

            Mockito.verify(spyRepository).getBadgesResultStream()
        }

    @Test
    fun `test set order filter true should call repository to set order filter asc`() =
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(null).`when`(spyRepository).setFilterOrder("asc")

            badgesViewModel.setFilterOrder(true)

            Mockito.verify(spyRepository).setFilterOrder("asc")
        }

    @Test
    fun `test set order filter false should call repository to set order filter desc`() =
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(null).`when`(spyRepository).setFilterOrder("desc")

            badgesViewModel.setFilterOrder(false)

            Mockito.verify(spyRepository).setFilterOrder("desc")
        }

    @Test
    fun `test set filters rank gold should call repository to set gold filter`() =
        testCoroutineRule.runBlockingTest {
            val max = "gold"
            Mockito.doReturn(null).`when`(spyRepository).setFilterMax(max)
            Mockito.doReturn(null).`when`(spyRepository).setFilterMin(null)

            badgesViewModel.setFilterByRank(R.id.gold_radio_button)

            Mockito.verify(spyRepository).setFilterMax(max)
            Mockito.verify(spyRepository).setFilterMin(null)
        }

    @Test
    fun `test set filters rank silver should call repository to set silver filter`() =
        testCoroutineRule.runBlockingTest {
            val max = "silver"
            val min = "silver"
            Mockito.doReturn(null).`when`(spyRepository).setFilterMax(max)
            Mockito.doReturn(null).`when`(spyRepository).setFilterMin(min)

            badgesViewModel.setFilterByRank(R.id.silver_radio_button)

            Mockito.verify(spyRepository).setFilterMax(max)
            Mockito.verify(spyRepository).setFilterMin(min)
        }

    @Test
    fun `test set filters rank bronze should call repository to set bronze filter`() =
        testCoroutineRule.runBlockingTest {
            val min = "bronze"
            Mockito.doReturn(null).`when`(spyRepository).setFilterMax(null)
            Mockito.doReturn(null).`when`(spyRepository).setFilterMin(min)

            badgesViewModel.setFilterByRank(R.id.bronze_radio_button)

            Mockito.verify(spyRepository).setFilterMax(null)
            Mockito.verify(spyRepository).setFilterMin(min)
        }

    @Test
    fun `test set filters rank all should call repository to set all filter`() =
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(null).`when`(spyRepository).setFilterMax(null)
            Mockito.doReturn(null).`when`(spyRepository).setFilterMin(null)

            badgesViewModel.setFilterByRank(R.id.all_radio_button)

            Mockito.verify(spyRepository).setFilterMax(null)
            Mockito.verify(spyRepository).setFilterMin(null)
        }
}