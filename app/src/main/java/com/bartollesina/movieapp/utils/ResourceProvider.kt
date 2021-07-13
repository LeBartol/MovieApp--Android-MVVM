package com.bartollesina.movieapp.utils

import android.content.Context


class ResourceProvider(private val context: Context) {
    fun getString(stringId: Int): String {
        return context.getString(stringId)
    }
}