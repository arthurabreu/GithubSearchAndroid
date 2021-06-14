package com.arthur.github.core.data.network.services

import com.arthur.github.core.data.network.model.Repos
import com.arthur.github.core.data.network.util.NetworkResult

interface GithubService {
    suspend fun getRepositories(query: String): NetworkResult<Repos>
}