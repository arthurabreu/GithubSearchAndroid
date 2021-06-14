package com.arthur.github.core.data.network.model

import com.google.gson.annotations.SerializedName

data class Item(
        @SerializedName("id")
        val id: Int,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("html_url")
        val htmlUrl: String
)
