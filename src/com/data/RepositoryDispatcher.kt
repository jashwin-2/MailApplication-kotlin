package com.data



object RepositoryDispatcher {
    private var repositorys : MutableMap<String, MailRepository>? = null

    fun addInRepositories(repository : MailRepository) {
        if (repositorys == null) repositorys = mutableMapOf()
        repositorys!![repository.domainName] = repository
    }

    fun getRepository(domainName : String)= repositorys?.get(domainName)

}