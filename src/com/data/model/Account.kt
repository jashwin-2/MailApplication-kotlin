package com.data.model

data class Account(var name:String, val mailId : MailId, var password: String, var mobileNo:Int) {
     var allMails  : MutableList<Mail>? = null

   infix fun addInMail(mail : Mail) = if (allMails!=null) allMails!!.add(mail) else allMails= mutableListOf(mail)

}