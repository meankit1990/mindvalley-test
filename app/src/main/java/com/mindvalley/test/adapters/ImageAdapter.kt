package com.mindvalley.test.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import com.mindvalley.asyncdownloadlib.models.DownloadTypeImage
import com.mindvalley.asyncdownloadlib.utils.DownloadProvider
import com.mindvalley.test.R
import java.util.ArrayList

/**
 * Created by Ankit Kumar Jain on 3/11/2019.
 */


class ImageAdapter// Constructor
    (private val mContext: Context, imgUrlArray: ArrayList<String>) : BaseAdapter() {
    private val mProvider: DownloadProvider?

    // Keep all Images in array
    private var imgUrlArray = ArrayList<String>()

    init {
        this.imgUrlArray = imgUrlArray
        mProvider = DownloadProvider.getInstance()
    }

    fun setImgUrlArray(imgUrlArray: ArrayList<String>) {
        this.imgUrlArray = imgUrlArray
    }

    override fun getCount(): Int {
        return imgUrlArray.size
    }

    override fun getItem(position: Int): Any {
        return imgUrlArray[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val imageView = ImageView(mContext)

        val mDataTypeImageCancel = DownloadTypeImage(imgUrlArray[position], object : DataDownloadCallbacks {
            override fun onStart(mDownloadDataType: BaseDownloadResource) {

            }
            override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                imageView.setImageBitmap((mDownloadDataType as DownloadTypeImage).imageBitmap)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = AbsListView.LayoutParams(250, 250)
            }

            override fun onFailure(
                mDownloadDataType: BaseDownloadResource,
                statusCode: Int,
                errorResponse: ByteArray?,
                e: Throwable?
            ) {
                imageView.setImageResource(R.drawable.ic_launcher_background)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.layoutParams = AbsListView.LayoutParams(250, 250)
            }

        })
        mProvider?.getRequest(mDataTypeImageCancel)
        return imageView
    }

}