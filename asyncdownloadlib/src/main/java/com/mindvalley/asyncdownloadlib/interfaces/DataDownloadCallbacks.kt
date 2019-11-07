package com.mindvalley.asyncdownloadlib.interfaces

import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
interface DataDownloadCallbacks {
    fun onStart(mDownloadDataType: BaseDownloadResource)

    fun onSuccess(mDownloadDataType: BaseDownloadResource)

    fun onFailure(mDownloadDataType: BaseDownloadResource, statusCode: Int, errorResponse: ByteArray?, e: Throwable?)

}
