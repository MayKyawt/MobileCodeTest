package com.mkt.mobilecodetest.common

import android.content.Context
import android.content.SharedPreferences


open class PreferencesHelper(context: Context) {

    companion object {
        private val PREF_BUFFER_PACKAGE_NAME = "com.mkt.mobilecodetest"

        private val PREF_KEY_NEXT_PAGE = "next_page"
    }

    private val pref: SharedPreferences

    init {
        pref = context.getSharedPreferences(PREF_BUFFER_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var nextPage: Int
        get() = pref.getInt(PREF_KEY_NEXT_PAGE, 1)
        set(nextPage) = pref.edit().putInt(PREF_KEY_NEXT_PAGE, nextPage).apply()

}