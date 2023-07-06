package com.hyunprise.android.api.member.vo

import java.sql.Timestamp

data class Member (
    val memberUUID: String? = null,
    val memberEmail: String? = null,
    val memberName: String? = null,
    val membershipPoint: Int? = null,
    val gender: Int? = null,
    val birthdate: Timestamp? = null,
    val address: String? = null,
    val contact: String? = null,
    val accountType: Int? = null,
    val brandId: Long? = null
)