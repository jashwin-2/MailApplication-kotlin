package com.data



object RepositoryDispatcher {
    private var repositorys : MutableMap<String, MailRepository>? = null

    infix fun addInRepositories(repository : MailRepository) {
        if (repositorys == null) repositorys = mutableMapOf()
        repositorys!![repository.domainName] = repository
    }

    infix fun getRepository(domainName : String)= repositorys?.get(domainName)

}