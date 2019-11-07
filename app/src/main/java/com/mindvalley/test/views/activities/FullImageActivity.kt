package com.mindvalley.test.views.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import com.mindvalley.asyncdownloadlib.models.DownloadTypeImage
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import com.mindvalley.test.R
import com.mindvalley.test.constants.AppConstants
import com.mindvalley.test.databinding.ActivityFullImageBinding
import kotlinx.android.synthetic.main.activity_full_image.*

/**
 * Created by Ankit Kumar Jain on 11/06/2019.
 */


class FullImageActivity : Activity() {
    private var mProvider: DownloadProvider? = null
    private lateinit var fullImageBinding: ActivityFullImageBinding
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_full_image)

        mProvider = DownloadProvider.getInstance()

        val i = intent
        var url: String?
        if (i.extras != null) {
            url = i.extras?.getString(AppConstants.FULL_IMAGE_URL)
        } else {
            throw Exception("No Url Provided")
        }
        val mDataTypeImageCancel = url?.let {
            DownloadTypeImage(it, object : DataDownloadCallbacks {
                override fun onStart(mDownloadDataType: BaseDownloadResource) {
                    progress_bar.visibility = View.VISIBLE
                }

                override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                    full_image_view.setImageBitmap((mDownloadDataType as DownloadTypeImage).imageBitmap)
                    progress_bar.visibility = View.GONE
                }

                override fun onFailure(
                    mDownloadDataType: BaseDownloadResource,
                    statusCode: Int,
                    errorResponse: ByteArray?,
                    e: Throwable?
                ) {
                    full_image_view.setImageResource(R.drawable.ic_menu_gallery)
                    progress_bar.visibility = View.GONE
                }

            })
        }
        mDataTypeImageCancel?.let { mProvider?.getRequest(it) }
    }

}