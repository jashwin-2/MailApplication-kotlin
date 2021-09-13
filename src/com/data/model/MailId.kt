package com.data.model

import com.exception.InvalidMailIdException

import com.extensions.isValidMailId

class MailId(id : String) {
    var id : String
   lateinit var domain : String
   lateinit var name: String
    init {
            if (id.isValidMailId()){
                    this.id = id
                    setFields()
                }
                else{
                    throw InvalidMailIdException()
            }
    }

    private fun setFields() {
        var strings : List<String> = id.split("@")
        name = strings[0].split("[0-9]")[0]
        domain = strings[1]
    }

    override fun toString(): String {
        return id
    }
}
