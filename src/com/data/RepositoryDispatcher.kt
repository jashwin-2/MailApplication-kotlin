package com.data



object RepositoryDispatcher {
    private var repositorys : MutableMap<String, MailRepository>? = null

    infix fun addInRepositorys(repository : MailRepository) {
        if (repositorys != null) repositorys!![repository.domainName] = repository
        else repositorys = mutableMapOf(repository.domainName to repository)
    }
    infix fun getRepository(domainName : String)= repositorys?.get(domainName)

}