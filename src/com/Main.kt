package com


import ZohoRepository
import com.data.GmailRepository
import com.data.RepositoryDispatcher
import com.view.MailApplicationView
import com.view.MenuItem

fun main() {

    RepositoryDispatcher.addInRepositories(GmailRepository)
    RepositoryDispatcher.addInRepositories(ZohoRepository)


    while (true) {
        println("Which application do you want to open \n1-->  Zoho Mail\n2-->  Gmail")
        if (readLine()?.toInt() == 1) MailApplicationView(ZohoRepository,"Zoho").logInView()
        else MailApplicationView(GmailRepository,"Gmail").logInView()
    }

}
