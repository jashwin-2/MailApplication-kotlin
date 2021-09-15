package com.extensions


import java.util.regex.Pattern

fun String.isValidMailId() = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(this).matches()

