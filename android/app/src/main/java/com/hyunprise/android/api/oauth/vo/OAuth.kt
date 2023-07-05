package com.hyunprise.android.api.oauth.vo

data class OAuth (
    var memberUUID: String? = null,
    var provider: OAuthProvider? = null,
    var authToken: String? = null,
)
