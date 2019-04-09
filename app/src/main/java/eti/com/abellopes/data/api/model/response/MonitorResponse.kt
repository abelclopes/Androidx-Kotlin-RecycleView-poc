package eti.com.abellopes.data.api.model.response

import com.google.gson.annotations.SerializedName
import eti.com.abellopes.data.api.model.MonitorData

data class MonitorResponse(
    val success: Boolean,
    @SerializedName("data")
    val monitorData: MonitorData?
)