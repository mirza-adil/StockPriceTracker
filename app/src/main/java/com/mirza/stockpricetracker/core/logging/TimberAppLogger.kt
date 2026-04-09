package com.mirza.stockpricetracker.core.logging

import timber.log.Timber

class TimberAppLogger : AppLogger {

    override fun d(tag: String, message: String, throwable: Throwable?) {
        val tree = Timber.tag(tag)
        if (throwable != null) tree.d(throwable, message) else tree.d(message)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        val tree = Timber.tag(tag)
        if (throwable != null) tree.i(throwable, message) else tree.i(message)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        val tree = Timber.tag(tag)
        if (throwable != null) tree.w(throwable, message) else tree.w(message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        val tree = Timber.tag(tag)
        if (throwable != null) tree.e(throwable, message) else tree.e(message)
    }
}
