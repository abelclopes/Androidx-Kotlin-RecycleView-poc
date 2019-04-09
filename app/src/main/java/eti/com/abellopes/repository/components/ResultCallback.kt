package eti.com.abellopes.repository.components

import eti.com.abellopes.data.api.model.response.FailureResponse

interface ResultCallback<T> {
    fun onSuccess(result: T)
    fun onFailure(result: FailureResponse?, t: Throwable?)
}