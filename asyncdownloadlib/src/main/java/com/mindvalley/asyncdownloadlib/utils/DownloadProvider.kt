package com.mindvalley.asyncdownloadlib.utils

import com.loopj.android.http.AsyncHttpClient
import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks
import com.mindvalley.asyncdownloadlib.interfaces.ProvideCallbacks
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource

import java.util.HashMap
import java.util.LinkedList

/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 * This class to contact with MDownloadAsyncManager and MCachingManager
 * This class to manage the requests that go to MDownloadAsyncManager
 * If two or more request for the same url this class will manage them
 */
class DownloadProvider private constructor() {
    private val allRequestsByKey = HashMap<String, LinkedList<BaseDownloadResource>>()
    private val allRequestsClient = HashMap<String, AsyncHttpClient>()
    private val mCachingManager: CacheManager? = CacheManager.getInstance()

    val isRequestDone: Boolean
        get() = allRequestsByKey.size == 0

    fun getRequest(mDownloadDataType: BaseDownloadResource) {
        val mKey = mDownloadDataType.keyMD5

        // Check if exist in the cache
        val mDownloadDataTypeFromCache = mCachingManager?.getMDownloadDataType(mKey)
        if (mDownloadDataTypeFromCache != null) {
            mDownloadDataTypeFromCache.comeFrom = "Cache"
            // call interface
            val imDownloadDataType = mDownloadDataType.imDownloadDataType
            imDownloadDataType.onStart(mDownloadDataTypeFromCache)
            imDownloadDataType.onSuccess(mDownloadDataTypeFromCache)
            return
        }

        // This will run if two request come for same url
        if (allRequestsByKey.containsKey(mKey)) {
            mDownloadDataType.comeFrom = "Cache"
            allRequestsByKey[mKey]?.add(mDownloadDataType)
            return
        }

        // Put it in the allRequestsByKey to manage it after done or cancel
        if (allRequestsByKey.containsKey(mKey)) {
            allRequestsByKey[mKey]?.add(mDownloadDataType)
        } else {
            val lstMDDataType = LinkedList<BaseDownloadResource>()
            lstMDDataType.add(mDownloadDataType)
            allRequestsByKey[mKey] = lstMDDataType
        }

        // Create new MDownloadDataType by clone it from the parameter
        val newMDownloadDataType = mDownloadDataType.getCopyOfMe(object : DataDownloadCallbacks {
            override fun onStart(mDownloadDataType: BaseDownloadResource) {
                allRequestsByKey[mDownloadDataType.keyMD5]?.forEach { m ->
                    m.data = mDownloadDataType.data
                    m.imDownloadDataType.onStart(m)
                }
            }

            override fun onSuccess(mDownloadDataType: BaseDownloadResource) {
                allRequestsByKey[mDownloadDataType.keyMD5]?.forEach { m ->
                    m.data = mDownloadDataType.data
                    m.imDownloadDataType.onSuccess(m)
                }
                allRequestsByKey.remove(mDownloadDataType.keyMD5)
            }

            override fun onFailure(
                mDownloadDataType: BaseDownloadResource,
                statusCode: Int,
                errorResponse: ByteArray?,
                e: Throwable?
            ) {
                allRequestsByKey[mDownloadDataType.keyMD5]?.forEach { m ->
                    m.data = mDownloadDataType.data
                    m.imDownloadDataType.onFailure(m, statusCode, errorResponse, e)
                }
                allRequestsByKey.remove(mDownloadDataType.keyMD5)
            }

        })

        // Get from download manager
        val mDownloadAsyncManager = AsyncDownloadManager()
        val client = mDownloadAsyncManager[newMDownloadDataType, object : ProvideCallbacks {
            override fun onComplete(mDownloadDataType: BaseDownloadResource) {
                // put in the cache when mark as done
                mCachingManager?.putMDownloadDataType(mDownloadDataType.keyMD5, mDownloadDataType)
                allRequestsClient.remove(mDownloadDataType.keyMD5)
            }

            override fun onFailure(mDownloadDataType: BaseDownloadResource) {
                allRequestsClient.remove(mDownloadDataType.keyMD5)
            }

            override fun onCancel(mDownloadDataType: BaseDownloadResource) {
                allRequestsClient.remove(mDownloadDataType.url)
            }
        }]

        allRequestsClient[mKey] = client
    }

    fun clearCache() {
        mCachingManager?.clearCache()
    }
    fun cancelRequest(mDownloadDataType: BaseDownloadResource) {
        if (allRequestsByKey.containsKey(mDownloadDataType.keyMD5)) {
            allRequestsByKey[mDownloadDataType.keyMD5]?.remove(mDownloadDataType)
            mDownloadDataType.comeFrom = "Canceled"
            mDownloadDataType.imDownloadDataType.onFailure(mDownloadDataType, 0, null, null)
        }
    }
    companion object {
        private var instance: DownloadProvider? = null

        fun getInstance(): DownloadProvider? {
            if (instance == null) {
                instance = DownloadProvider()
            }
            return instance
        }
    }
}
