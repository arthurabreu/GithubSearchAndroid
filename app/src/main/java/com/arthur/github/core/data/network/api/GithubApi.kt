package com.arthur.github.core.data.network.api

import com.arthur.github.core.data.network.model.Repos
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET(GET_REPO)
    suspend fun getRepos(@Query("q") query: String): Repos

    companion object {
        private const val GET_REPO = "repositories"
    }
}