package com.mindvalley.test.interfaces

import java.util.ArrayList
interface JsonDataCallBack {
    fun onSuccess(responseData:ArrayList<*>)
    fun onFailure(throwable: Throwable)
}