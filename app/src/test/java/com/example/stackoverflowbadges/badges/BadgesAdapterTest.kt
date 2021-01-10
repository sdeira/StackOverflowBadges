package com.example.stackoverflowbadges.badges

import android.os.Build
import android.widget.LinearLayout
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import java.com.example.stackoverflowbadges.TestCoroutineRule
import com.example.stackoverflowbadges.model.Badge
import com.example.stackoverflowbadges.model.UiModel
import com.example.stackoverflowbadges.ui.badges.BadgeViewHolder
import com.example.stackoverflowbadges.ui.badges.BadgesAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class BadgesAdapterTest {
    lateinit var adapter: BadgesAdapter

    @get:Rule
    val testCoroutineRule =
        TestCoroutineRule()

    @Before
    fun setUp() {
        adapter = Mockito.spy(BadgesAdapter::class.java)
    }

    @Test
    fun testCreateViewHolder() {
        val viewHolder = adapter.onCreateViewHolder(LinearLayout(ApplicationProvider.getApplicationContext()), 0)
        Assert.assertNotNull(viewHolder)
    }

    @Test
    fun testBindViewHolder() {
        testCoroutineRule.runBlockingTest {
            val viewHolder: BadgeViewHolder =
                Mockito.spy(
                    adapter.onCreateViewHolder(
                        LinearLayout(ApplicationProvider.getApplicationContext()),
                        0
                    )
                )
            val list = ArrayList<UiModel.BadgeItem>()
            val badge = UiModel.BadgeItem(
                Badge("1", "named", "gold", "author",
                "2", 3, "description")
            )
            list.add(badge)
            val pagingData: PagingData<UiModel.BadgeItem> = PagingData.from(list)
            adapter.submitData(pagingData)

            adapter.bindViewHolder(viewHolder, 0)

            Mockito.verify(viewHolder).bind(badge)
        }
    }
}