package com.mindvalley.asyncdownloadlib

import android.util.Log
import android.widget.AbsListView
import android.widget.ImageView
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import com.mindvalley.asyncdownloadlib.models.DataTypeEnum
import com.mindvalley.asyncdownloadlib.models.DownloadTypeImage
import com.mindvalley.asyncdownloadlib.models.DownloadTypeJson
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.mindvalley.asyncdownloadlib.test", appContext.packageName)
    }

    @Test
    fun getView() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val mProvider = DownloadProvider.getInstance()
        val imageView = ImageView(appContext)
        val mDataTypeImageCancel = DownloadTypeImage(
            "https://images.unsplash.com/profile-1464495186405-68089dcd96c3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32&s=63f1d805cffccb834cf839c719d91702",
            object : DataDownloadCallbacks {
                override fun onStart(mDownloadDataType: BaseDownloadResource) {

                }

                override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                    imageView.setImageBitmap((mDownloadDataType as DownloadTypeImage).imageBitmap)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.layoutParams = AbsListView.LayoutParams(250, 250)
                    assert(true) { "Succcess" }
                }

                override fun onFailure(
                    mDownloadDataType: BaseDownloadResource,
                    statusCode: Int,
                    errorResponse: ByteArray?,
                    e: Throwable?
                ) {
                    imageView.setImageResource(R.drawable.abc_ic_ab_back_material)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    imageView.layoutParams = AbsListView.LayoutParams(250, 250)
                }
            })
        mProvider?.getRequest(mDataTypeImageCancel)
    }

    @Test
    fun getJsonResponse() {
        val mProvider = DownloadProvider.getInstance()
        val mDataTypeJson = DownloadTypeJson("https://pastebin.com/raw/wgkJgazE", object : DataDownloadCallbacks {
            override fun onStart(mDownloadDataType: BaseDownloadResource) {
                Log.e("TESTING", "START")
            }

            override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                Log.e("TESTING", "SUCCESS")
                assertEquals(DataTypeEnum.IMAGE, mDownloadDataType.getmDataType())
            }

            override fun onFailure(
                mDownloadDataType: BaseDownloadResource,
                statusCode: Int,
                errorResponse: ByteArray?,
                e: Throwable?
            ) {
                Log.e("TESTING", "FAILURE")

            }

        })
        mProvider?.getRequest(mDataTypeJson)
    }
}
