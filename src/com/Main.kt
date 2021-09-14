package com


import ZohoRepository
import com.data.GmailRepository
import com.data.RepositoryDispatcher
import com.view.MailApplicationView
import com.view.MenuItem

fun main() {
 var item = MenuItem.values()
   RepositoryDispatcher addInRepositorys GmailRepository
    RepositoryDispatcher addInRepositorys ZohoRepository

//    var mail = Mail(MailId("ram@gmail.com"),MailId("sai@zoho.com"),Mail.Type.SENT,"hi")
//    GmailRepository sendMail mail
//    GmailRepository.getAccount ("ram@gmail.com")?.allMails?.forEach{ println(it)}
//    ZohoRepository.getAccount("sai@zoho.com")?.allMails?.forEach{println(it)}
//
//   MailApplicationView(GmailRepository,"Gmail").logInView()

   // var choice= readLine()?.toInt()
    while (true) {
        println("Which application do you want to open \n1-->  Zoho Mail\n2-->  Gmail")
        if (readLine()?.toInt() == 1) MailApplicationView(ZohoRepository,"Zoho").logInView()
        else MailApplicationView(GmailRepository,"Gmail").logInView()
    }

}
