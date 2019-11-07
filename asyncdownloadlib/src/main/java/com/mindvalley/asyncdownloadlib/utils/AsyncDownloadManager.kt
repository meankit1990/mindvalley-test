package com.mindvalley.asyncdownloadlib.utils


import android.os.Handler
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.mindvalley.asyncdownloadlib.interfaces.ProvideCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource
import cz.msebera.android.httpclient.Header
import android.os.Looper



/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
class AsyncDownloadManager {

    operator fun get(mDownloadDataType: BaseDownloadResource, imProvider: ProvideCallbacks): AsyncHttpClient {
        val mHandler = Handler(Looper.getMainLooper())
        val client = AsyncHttpClient(true, 80, 443)
        val mRunnable = Runnable {
            client.get(mDownloadDataType.url, object : AsyncHttpResponseHandler() {
                override fun onStart() {
                    // called before request is started
                    mDownloadDataType.imDownloadDataType.onStart(mDownloadDataType)
                }

                override fun onSuccess(statusCode: Int, headers: Array<Header>, response: ByteArray) {
                    // called when response HTTP status is "200 OK"
                    mDownloadDataType.data = response
                    mDownloadDataType.imDownloadDataType.onSuccess(mDownloadDataType)

                    // This call for provider to manage it
                    imProvider.onComplete(mDownloadDataType)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    errorResponse: ByteArray,
                    e: Throwable
                ) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    mDownloadDataType.imDownloadDataType.onFailure(mDownloadDataType, statusCode, errorResponse, e)

                    // This call for provider to manage it
                    imProvider.onFailure(mDownloadDataType)
                }

                override fun onCancel() {
                    super.onCancel()

                    // called when request is retried
                    imProvider.onCancel(mDownloadDataType)
                }
            })
        }
        mHandler.post(mRunnable)
        return client
    }
}


