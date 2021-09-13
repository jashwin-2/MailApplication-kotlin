package com.data

import  com.data.model.Account
import com.data.model.Mail

abstract class MailRepository(val domainName: String) {

    var accounts : MutableMap<String,Account> = mutableMapOf()

    fun contains(id : String) = accounts.containsKey(id)
    infix fun getAccount(id : String) = accounts[id]
    fun Authenticate(id : String,password : String) : Boolean {
        return accounts[id]?.password == password
    }
    infix fun addAccount(account : Account) = accounts.put(account.mailId.id,account)

    infix fun sendMail(mail : Mail)
    {
        var receiverMailId = mail.receiver
        var sender = accounts[mail.sender.id]
        val receiverMail : Mail = mail.copy(type = Mail.Type.RECEIVED)
        if(domainName == receiverMailId.domain) {
            sender!! addInMail mail
            accounts[receiverMailId.id]!! addInMail(receiverMail)
        }
        else{
            var receiverRepository: MailRepository? = RepositoryDispatcher getRepository receiverMailId.domain

            if (receiverRepository != null) {
                receiverRepository receiveMail receiverMail
                sender!! addInMail mail
            }

        }
    }

    infix fun receiveMail(receiverMail: Mail) {
        accounts[receiverMail.receiver.id]!! addInMail(receiverMail)
    }

}