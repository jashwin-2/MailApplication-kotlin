package com.view

enum class MenuItem(val index: Int,val text: String) {
    COMPOSE_MAIL(1,"Compose a mail"),
    ALL_MAILS(2,"Show all mails"),
    SENT (3,"Show sent mails"),
    RECEIVED(4,"Show Received Mails"),
    DRAFT(5,"Draft Mails"),
    LOGOUT(6,"Log Out");
}