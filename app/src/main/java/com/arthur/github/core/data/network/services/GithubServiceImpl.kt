package com.arthur.github.core.data.network.services

import com.arthur.github.core.data.network.api.GithubApi
import com.arthur.github.core.data.network.model.Repos
import com.arthur.github.core.data.network.util.NetworkConst
import com.arthur.github.core.data.network.util.NetworkResult

class GithubServiceImpl(
        private val githubApi: GithubApi
) : GithubService {
    override suspend fun getRepositories(query: String): NetworkResult<Repos> {
        return try {
            NetworkResult.Success(githubApi.getRepos(query))
        } catch(e: Exception) {
            NetworkResult.Error(e.message ?: NetworkConst.GENERAL_EXCEPTION)
        }
    }
}