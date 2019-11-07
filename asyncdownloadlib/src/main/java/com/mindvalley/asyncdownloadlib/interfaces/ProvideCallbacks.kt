package com.mindvalley.asyncdownloadlib.interfaces


import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource

/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
interface ProvideCallbacks {
    fun onComplete(mDownloadDataType: BaseDownloadResource)
    fun onFailure(mDownloadDataType: BaseDownloadResource)
    fun onCancel(mDownloadDataType: BaseDownloadResource)
}
