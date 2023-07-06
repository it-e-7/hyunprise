package com.hyunprise.android.global.utils

private enum class AccountType(val level: Int) {
    MEMBER( 1), SELLER(10), ADMIN(99)
}

object AccountTypeChecker {
    fun isMember(accountType: Int): Boolean {
        return accountType == AccountType.MEMBER.level
    }
}