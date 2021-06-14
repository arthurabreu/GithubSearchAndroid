package com.arthur.github.core.data.network.services

import com.arthur.github.core.data.network.api.FakeGithubApi
import com.arthur.github.core.data.network.model.Repos
import com.arthur.github.core.data.network.util.NetworkResult
import com.arthur.github.core.data.network.util.NetworkUtils.GENERAL_EXCEPTION

class FakeGithubServiceImpl: GithubService {

    private val fakeGithubApi = FakeGithubApi()

    override suspend fun getRepositories(query: String): NetworkResult<Repos> {
        return when (query) {
            GENERAL_EXCEPTION -> {
                NetworkResult.Error(GENERAL_EXCEPTION)
            }
            else -> {
                NetworkResult.Success(fakeGithubApi.getRepos())
            }
        }
    }
}