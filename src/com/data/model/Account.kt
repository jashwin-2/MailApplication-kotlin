package com.data.model

data class Account(var name:String, val mailId: MailId, var password: String, var mobileNo: Long) {
     var allMails  : MutableList<Mail>? = null

    fun addInMail(mail : Mail): Unit {
       if (allMails==null)  allMails= mutableListOf()
       allMails!!.add(mail)
   }

}