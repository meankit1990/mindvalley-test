package com.mindvalley.test.views

import android.util.Log
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import com.mindvalley.asyncdownloadlib.models.DataTypeEnum
import com.mindvalley.asyncdownloadlib.models.DownloadTypeImage
import com.mindvalley.asyncdownloadlib.models.DownloadTypeJson
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import com.mindvalley.test.R
import com.mindvalley.test.views.activities.PinBoardActivity
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PinBoardActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<PinBoardActivity>
            = ActivityTestRule(PinBoardActivity::class.java)
    @Test
    fun testRefresh(){
        onView(withId(R.id.drawer_layout))
            .perform(DrawerActions.open())

        onView(withId(R.id.navigation_view))
            .perform(NavigationViewActions.navigateTo(R.id.nav_manage))
        Thread.sleep(500)

        onView(withId(R.id.action_refresh)).perform(click())
        Thread.sleep(5000)
    }
    @Test
    fun testJsonDownload(){
        val mProvider = DownloadProvider.getInstance()
        val mDataTypeJson = DownloadTypeJson("https://pastebin.com/raw/wgkJgazE", object : DataDownloadCallbacks {
            override fun onStart(mDownloadDataType: BaseDownloadResource) {
                Log.e("TESTING", "START")
            }

            override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                assertEquals(DataTypeEnum.JSON, mDownloadDataType.getmDataType())
            }

            override fun onFailure(
                mDownloadDataType: BaseDownloadResource,
                statusCode: Int,
                errorResponse: ByteArray?,
                e: Throwable?
            ) {
                assertFalse(false)
            }

        })
        mProvider?.getRequest(mDataTypeJson)
    }
    @Test
    fun testImageDownload(){
        val mProvider = DownloadProvider.getInstance()
        val mDataTypeJson = DownloadTypeImage("https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702", object : DataDownloadCallbacks {
            override fun onStart(mDownloadDataType: BaseDownloadResource) {
                Log.e("TESTING", "START")
            }
            override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                assertEquals(DataTypeEnum.IMAGE, mDownloadDataType.getmDataType())
            }

            override fun onFailure(
                mDownloadDataType: BaseDownloadResource,
                statusCode: Int,
                errorResponse: ByteArray?,
                e: Throwable?
            ) {
                assertFalse(false)
            }

        })
        mProvider?.getRequest(mDataTypeJson)
    }
}