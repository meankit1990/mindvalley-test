package com.mindvalley.asyncdownloadlib.utils

import android.util.Log
import android.util.LruCache
import com.mindvalley.asyncdownloadlib.models.BaseDownloadResource


/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
class CacheManager private constructor() {
    private val maxCacheSize: Int
    private val mDownloadDataTypeCache: LruCache<String, BaseDownloadResource>

    init {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        maxCacheSize = maxMemory / 8 // 4 * 1024 * 1024; // 4MiB
        mDownloadDataTypeCache = LruCache(maxCacheSize)
    }

    fun getMDownloadDataType(key: String): BaseDownloadResource? {
        return mDownloadDataTypeCache.get(key)
    }

    fun putMDownloadDataType(key: String, mDownloadDataType: BaseDownloadResource): Boolean {
        return mDownloadDataTypeCache.put(key, mDownloadDataType) != null
    }

    fun clearCache() {
        mDownloadDataTypeCache.evictAll()
    }

    companion object {
        private var instance: CacheManager? = null

        fun getInstance(): CacheManager? {
            if (instance == null) {
                instance = CacheManager()
            }
            return instance
        }
    }
}
