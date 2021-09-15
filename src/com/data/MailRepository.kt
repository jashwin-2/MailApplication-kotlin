package com.data

import  com.data.model.Account
import com.data.model.Mail
import com.data.model.MailId

abstract class MailRepository(val domainName: String) {

    private var accounts : MutableMap<String,Account> = mutableMapOf()

    fun contains(id : String) = accounts.containsKey(id)

    fun getAccount(id : String) = accounts[id]

    fun Authenticate(id : String,password : String) : Boolean {
        return accounts[id]?.password == password
    }
     infix fun add(account : Account) = accounts.put(account.mailId.id,account)

    fun sendMail(mail : Mail)
    {
        val receiverMailId = mail.receiver
        val sender : Account = accounts[mail.sender.id]!!
        val receiverMail : Mail = mail.copy(type = Mail.Type.RECEIVED)

        if(domainName == receiverMailId.domain) {
            sender.addInMail(mail)
            accounts[receiverMailId.id]!!.addInMail(receiverMail)
        }
        else{
            val receiverRepository: MailRepository? = RepositoryDispatcher.getRepository(receiverMailId.domain)

            if (receiverRepository != null) {
                receiverRepository receive receiverMail
                sender.addInMail(mail)
            }

        }
    }

    private infix fun receive(receiverMail: Mail) {
        accounts[receiverMail.receiver.id]!!.addInMail(receiverMail)
    }

    infix fun isValid(mailId : MailId): Boolean{
        val domainName: String = mailId.domain
        return if (domainName == this.domainName) contains(mailId.id) else (RepositoryDispatcher.getRepository(domainName)
            ?.contains(mailId.id) == true)
    }

}