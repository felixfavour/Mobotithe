package com.felixfavour.mobotithe.util

import android.content.Context

const val PREF = "my_preferences"
const val IS_USER_LOGGED_IN = "is_user_logged_in"

enum class LoginTokens {
    // PASS means user has logged in
    PASS,
    // FAIL means user has not logged in
    FAIL}

/*
    loginToken contains a <code>LoginTokens</code>
    It is called by the setLoginToken to authorize
    a user if he passes.
*/


var loginToken = LoginTokens.FAIL

fun setLoginToken(context: Context, tokens: LoginTokens) {
    val sharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    loginToken = tokens
    if (tokens == LoginTokens.PASS) {
        sharedPreferences.edit().putBoolean(IS_USER_LOGGED_IN, true).commit()
    } else if(tokens == LoginTokens.FAIL) {
        sharedPreferences.edit().putBoolean(IS_USER_LOGGED_IN, false).commit()
    }
}