package com.data.model

data class Account(var name:String, val mailId : MailId, var password: String, var mobileNo:Int) {
     var allMails  : MutableList<Mail>? = null

   infix fun addInMail(mail : Mail): Unit {
       if (allMails==null)  allMails= mutableListOf()
       allMails!!.add(mail)
   }

}