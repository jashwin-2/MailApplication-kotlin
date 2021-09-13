package com.data.model

data class Mail(
    val sender: MailId,
    val receiver: MailId,
    val type: Type,
    val subject: String = " ",
    val body: String = " ",
    val attachment: String = " "
) {

    enum class Type(val text: String) {
        SENT("Sent Mails"), RECEIVED("Received Mails"), DRAFT("Draft Mails");

        override fun toString(): String {
            return when (this) {
                SENT -> "Sent"
                RECEIVED -> "Received"
                DRAFT -> "Draft"
            }
        }
    }
}