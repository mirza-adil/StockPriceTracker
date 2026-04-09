package com.mirza.stockpricetracker.presentation.screens.livequotes

import androidx.annotation.StringRes

sealed interface LiveQuotesEffect {
    data class NotifySuccess(@param:StringRes val messageResId: Int) : LiveQuotesEffect
    data class NotifyError(@param:StringRes val messageResId: Int) : LiveQuotesEffect
    data class NotifyInfo(@param:StringRes val messageResId: Int) : LiveQuotesEffect
}
