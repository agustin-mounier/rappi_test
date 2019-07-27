package com.example.rappitest.repository.remote

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * TODO: explain a little.
 */
class TmdbCallback<T>(
    private val isLoading: MutableLiveData<Boolean>,
    private val requestErrorAction: MutableLiveData<RequestAction>,
    private val requestAction: RequestAction,
    private val onSuccessFun: (T?) -> Unit
) : Callback<T> {

    init {
        isLoading.value = true
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        isLoading.value = false
        requestErrorAction.value = requestAction
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        isLoading.value = false
        onSuccessFun(response.body())
    }

}