package com.data

import com.data.model.Account
import com.data.model.MailId

object GmailRepository : MailRepository("gmail.com"){
    //Filling mock data
    init {
        add (Account("Ram", MailId("ram@gmail.com"),"1234",4343545))

        add (Account("Rex", MailId("rex@gmail.com"),"1234",4343545))
    }
}