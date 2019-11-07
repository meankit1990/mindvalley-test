package com.mindvalley.test.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindvalley.test.repositories.PinboardRepository

class PinboardFragmentViewModel : ViewModel() {
    fun downloadJsonType(): MutableLiveData<PinboardRepository.Companion.DownloadTypeResponse>? {
        return PinboardRepository.getInstance()?.downloadJsonType()
    }
}