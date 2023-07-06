package com.hyunprise.android.global.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    fun timestampToYYYYMMDD(timestamp: Timestamp): String {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(timestamp)
    }
    fun toIssuedCouponExpireDateString(timestamp: Timestamp): String {
        return "${timestampToYYYYMMDD(timestamp)}"
    }
}