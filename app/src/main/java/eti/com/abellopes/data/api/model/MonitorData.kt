package eti.com.abellopes.data.api.model

data class MonitorData (
    val success: Boolean,
    val date: String?,
    val code: Int?,
    val retrying: Boolean?,
    val times: Int?,
    val message: String?
)