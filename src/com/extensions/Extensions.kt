package com.extensions

import com.data.model.Mail
import com.data.model.MailId
import com.view.MenuItem
import java.util.regex.Pattern

fun String.isValidMailId() = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(this).matches()

infix fun Int.values(items : Array<MenuItem>) = items.find { it.index==this }