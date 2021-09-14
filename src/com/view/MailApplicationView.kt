package com.view

import com.data.MailRepository
import com.data.RepositoryDispatcher
import com.data.model.Account
import com.data.model.Mail
import com.data.model.MailId
import com.exception.InvalidMailIdException
import com.extensions.isValidMailId
import com.extensions.values


class MailApplicationView(private val repository: MailRepository, private val name: String ) {
    lateinit var account: Account
    private val item = MenuItem.values()
    public fun logInView()
    {
        println("************** WELCOME TO $name Mails **********\n Enter your choice 1 -> Login " +
                " 2 -> Create account 3->Exit")
        var choice:Int = 0
        try {
            choice = readLine()?.toInt() ?: 0
        }catch (ex : NumberFormatException){

        }
        when (choice) {
            1 -> login()
            2 -> createAccount()
            3 -> return
            else -> {
                println("Invalid Input ")
                logInView()
            }
        }

    }

    private fun createAccount() {
        println("Enter your name ")
        val name : String? = readLine()
        val customerMailId : MailId
        var mobileNo : Int = 0

        while (true)
        {
            if(mobileNo == 0)
                try {
                    println("Enter your Mobile number")
                    mobileNo = readLine()?.toInt() ?: throw NumberFormatException()
                }catch (ex : NumberFormatException){
                    println("Enter a valid mobile no ")
                    continue
                }
            println("Enter the mail id you want without domain name:")
            var mailId = readLine() ?:  continue
            if (mailId.contains('@')) {
                println("@ is not allowed ")
                continue
            }
            mailId+="@"+repository.domainName
            if(!mailId.isValidMailId())
                println("Entered mail id is invalid only use Alphabets,Numbers, . and _ ")
            if(repository.contains(mailId)) {
                println("Mail id $mailId is already taken enter a different one")
                continue
            }
            customerMailId = MailId(mailId)
            break
        }
        println("Enter the password :")
        val password = readLine()
        repository.addAccount(Account(name!!, customerMailId, password!!,mobileNo))
        println("Account created Successfully ....!\nMail Id : $customerMailId \n Password : $password")
        logInView()
    }

    private fun login() {
        println("Enter your mail Id ")
        var mailId : MailId? = null
        try {
            mailId = readLine()?.let { MailId(it) }
        }catch (ex : InvalidMailIdException)
        {
            println("Entered mail Id is invalid ")
            return
        }

        if(!repository.contains(mailId!!.id)){
            onErrorOccur(ErrorCodes.ACCOUNT_NOT_FOUND)
            logInView()
        }
        else{
            println("Enter your password ")
            if ((repository.getAccount(mailId.id)?.password ) .equals(readLine())){
                this.account= repository.getAccount(mailId.id)!!
                showMenuItems()
            }
            else {
                onErrorOccur(ErrorCodes.INVALID_PASSWORD)
                logInView()
            }
        }

    }

    private fun showMenuItems()
    {
        println("************ Menu *********")
        item.forEach { println("${it.index} --> ${it.text}") }
        var input : Int? = 0
        try {
        input = readLine()?.toInt()
        }catch (ex : NumberFormatException){

        }
        if (input != null)
            onMenuItemSelected (input values item)
    }

    private fun onMenuItemSelected(menuItem: MenuItem?) {
        when (menuItem) {
            MenuItem.COMPOSE_MAIL -> composeMail(null)
            MenuItem.ALL_MAILS -> showMails()
            MenuItem.RECEIVED -> showMails(Mail.Type.RECEIVED)
            MenuItem.SENT -> showMails(Mail.Type.SENT)
            MenuItem.DRAFT -> showMails(Mail.Type.DRAFT)
            MenuItem.LOGOUT -> {
                logInView()
                return
            }
        }
        showMenuItems()
    }

    private fun showMails() {
        val mails = account.allMails?.reversed()
        if (mails.isNullOrEmpty()){
            println("You don't have any mails to show")
            return
        }
        println("********All Mails********\nSno\tName\tSubject\tType")
        mails.mapIndexed { index, mail -> "${index+1}\t${mail.receiver.name}\t\t${mail.subject}\t${mail.type}" }
            .forEach { println(it) }
        getChoiceTOpen(mails,isCalledFromAllMails = true)
    }

    private fun showMails(type : Mail.Type) {
        val mails = account.allMails
            ?.reversed()
            ?.filter { it.type == type }
        if (mails.isNullOrEmpty()){
            println("You don't have $type mails to show")
            return
        }
        println("********${type.text}********\nSno\tName\tSubject")
        mails.mapIndexed { index, mail -> "${index+1}\t${mail.receiver.name}\t\t${mail.subject} " }.forEach { println(it) }
        getChoiceTOpen(mails,isCalledFromAllMails = false)
    }

    private fun getChoiceTOpen(mails : List<Mail> , isCalledFromAllMails : Boolean) {
        val choice : Int
        println("\nWhich one(Sno) do you want to open \t\t Press 0 to for previous menu")

        try {
            choice= readLine()?.toInt() ?: 0
            if(choice == 0)
                return
            onOpen(mails[choice-1])
        }catch(ex: Exception) {
            if (ex is NumberFormatException || ex is IndexOutOfBoundsException) {  println("Invalid input ")
                if (isCalledFromAllMails) showMails() else showMails(mails[0].type)}
            else throw ex
        }
    }

    private fun onOpen(mail : Mail){
        println("From : ${mail.sender}   \tTo :${mail.receiver}")
        println("Subject : ${mail.subject}\nBody : ${mail.body}\nAttachment : ${mail.attachment}")
        print("Enter your choice \n 0--> Back \t1-->Reply\t2-->Delete Mail\t")
        if (mail.type == Mail.Type.DRAFT) println("3--> Send Mail") else println()
        try {
            when (readLine()?.toInt()) {
                1 -> composeMail(
                    if (mail.type==Mail.Type.RECEIVED) mail.sender else mail.receiver
                )
                2 -> {
                    account.also { it.allMails?.remove(mail) }
                    println("Mail deleted Successfully...!")
                }
                3 -> {
                    if (mail.type != Mail.Type.DRAFT)
                        throw NumberFormatException()
                    mail.type = (Mail.Type.SENT)
                    account.also { it.allMails?.remove(mail) }
                    sendMail(mail)
                }
                0 -> return
                else -> onOpen(mail)
            }
        } catch (e: NumberFormatException) {
            println("Invalid input ")
            onOpen(mail)
        }
    }

    private fun composeMail(mailId: MailId?) {
        var receiverMailId = mailId
        if (receiverMailId == null) {
            println("Enter receivers Mail Id ")
            try{
                receiverMailId = MailId(readLine().toString())
                if (RepositoryDispatcher.getRepository(receiverMailId.domain) == null) {
                    onErrorOccur(ErrorCodes.DOMAIN_NOT_FOUND)
                    receiverMailId=null
                    composeMail(receiverMailId)
                }

                else if (!repository.isValid(receiverMailId)) {
                    onErrorOccur(ErrorCodes.ACCOUNT_NOT_FOUND)
                    receiverMailId=null
                    composeMail(receiverMailId)
                }

            } catch (exp: InvalidMailIdException) {
                println("Invalid mail Id ")
                composeMail(null)
            }
        }
        if (receiverMailId == null) return
        println(receiverMailId)
        println("************* COMPOSE *************\nFrom : 	${account.mailId}	 To: $receiverMailId")
        print("Subject : \t")
        val subject: String = readLine().toString()
        println()
        print("Body :\t")
        val body: String = readLine().toString()
        println()
        print("Add attachement  : \t")
        val attachment: String = readLine().toString()
        val mail = Mail(account.mailId, receiverMailId,Mail.Type.SENT, subject, body,attachment)
        while (true) {
            var choice : Int
            println("Enter your choice \n 1--> Send Mail \t 2--> Save as Draft")
            try {
                choice = readLine()?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                println("Invalid input")
                continue
            }
            when (choice) {
                //Send Mail
                1 -> {
                    sendMail(mail)
                    break
                }
                //Save in Draft
                2 -> {
                    mail.type = Mail.Type.DRAFT
                    account.addInMail(mail)
                    println("Mail saved as Draft Successfully...!")
                    break
                }
                else -> {
                    println("Invalid input")
                    continue
                }
            }

        }
    }
    private fun sendMail(mail: Mail) {
        repository.sendMail(mail)
        println("\nMail sent successfully..!\n")
    }

    private fun onErrorOccur(errorCode : Int){
        when(errorCode){
            1 ->  println("Invalid Mail Id Account not found")
            2 ->  println("Invalid Password")
            3 -> println("Domain not found")

        }
    }
}


