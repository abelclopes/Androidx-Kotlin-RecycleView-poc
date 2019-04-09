package eti.com.abellopes.data.api.model.response


import br.com.soha.feria.data.api.model.Detail

data class FailureResponse(
    val code: Int,
    val success: Boolean,
    val detail: Detail
    )