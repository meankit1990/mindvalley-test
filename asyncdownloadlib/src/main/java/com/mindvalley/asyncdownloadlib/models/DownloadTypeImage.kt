package com.mindvalley.asyncdownloadlib.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks


/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
class DownloadTypeImage(url: String, imDownloadDataType: DataDownloadCallbacks) :
    BaseDownloadResource(url, DataTypeEnum.IMAGE, imDownloadDataType) {

    val imageBitmap: Bitmap?
        get() = data?.size?.let { BitmapFactory.decodeByteArray(data, 0, it) }

    override fun getCopyOfMe(imDownloadDataType: DataDownloadCallbacks): BaseDownloadResource {
        return DownloadTypeImage(this.url, imDownloadDataType)
    }
}
