package com.mindvalley.asyncdownloadlib.models


import com.mindvalley.asyncdownloadlib.interfaces.DataDownloadCallbacks

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Ankit Kumar Jain on 11/05/2019.
 */
abstract class BaseDownloadResource protected constructor(
    val url: String,
    private val mDataType: DataTypeEnum,
    val imDownloadDataType: DataDownloadCallbacks
) {
    var data: ByteArray? = null

    val keyMD5: String

    // User For Just For Test
    var comeFrom = "Net"

    init {
        this.keyMD5 = md5(this.url)
    }

    fun getmDataType(): DataTypeEnum {
        return mDataType
    }

    abstract fun getCopyOfMe(imDownloadDataType: DataDownloadCallbacks): BaseDownloadResource


    private fun md5(s: String): String {
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance("MD5")
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (b in messageDigest) {
                val h = StringBuilder(Integer.toHexString(0xFF and b.toInt()))
                while (h.length < 2)
                    h.insert(0, "0")
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}
