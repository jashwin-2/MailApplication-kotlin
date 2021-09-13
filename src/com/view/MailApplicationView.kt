package com.view

import com.data.MailRepository
import com.data.model.Account
import com.extensions.values

class MailApplicationView(val repository: MailRepository,val name: String ) {
    lateinit var account: Account
    val item = MenuItem.values()
     public fun logInView()
     {
         println("************** WELCOME TO $name Mails **********\n Enter your choice 1 -> Login" +
                 "2 -> Create account 3->Exit")
         val choice = readLine()?.toInt()
         if(choice==1)
             login()
         else if (choice == 2)
             createAccount()
         else if(choice == 3)
             return
         else
         {
             println("Invalid Input ")
             logInView()
         }

     }

    private fun createAccount() {
        TODO("Not yet implemented")
    }

    private fun login() {
        TODO("Not yet implemented")
    }

    public fun showMenuItems()
    {
        println("************ Menu *********")
        item.forEach { println("${it.index} --> ${it.text}") }
        var input = readLine()?.toInt()
        if (input != null)
            println(input values item)
    }
}