package com.data


import com.data.model.Account
import com.data.model.MailId

object ZohoRepository : MailRepository("zoho.com"){
    //Filling mock data
    init {
        add (Account("Rex", MailId("rex@zoho.com"),"1234",4343545))

        add (Account("Sai", MailId("sai@zoho.com"),"1234",4343545))
    }
}