package com


import ZohoRepository
import com.data.GmailRepository
import com.data.MailRepository
import com.data.RepositoryDispatcher
import com.data.model.Mail
import com.data.model.MailId
import com.extensions.values
import com.view.MailApplicationView
import com.view.MenuItem

fun main() {
 var item = MenuItem.values()
   RepositoryDispatcher addInRepositorys GmailRepository
    RepositoryDispatcher addInRepositorys ZohoRepository

    var mail = Mail(MailId("ram@gmail.com"),MailId("sai@zoho.com"),Mail.Type.SENT,"hi")
    GmailRepository sendMail mail
    GmailRepository.getAccount ("ram@gmail.com")?.allMails?.forEach{ println(it)}
    ZohoRepository.getAccount("sai@zoho.com")?.allMails?.forEach{println(it)}

   MailApplicationView(GmailRepository,"Gmail").showMenuItems()
}
