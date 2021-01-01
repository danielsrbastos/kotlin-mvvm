package com.example.mvvm.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T, A> performGetOperation(
    mutableLiveData: MutableLiveData<Resource<T>>,
    databaseData: T,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
) {
    withContext(Dispatchers.IO) {
        mutableLiveData.postValue(Resource.loading())

        val result = Resource.success(databaseData)
        mutableLiveData.postValue(result)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS)
            saveCallResult(responseStatus.data!!)

        else if (responseStatus.status == Resource.Status.ERROR) {
            mutableLiveData.postValue(Resource.error(responseStatus.message!!))
            mutableLiveData.postValue(result)
        }
    }
}


