package com.finaltask.meatq

import com.google.gson.annotations.SerializedName

data class Response(
	@field:SerializedName("predicted_label")
	var predictedLabel: List<String?>? = null
)
