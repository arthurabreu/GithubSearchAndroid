package com.arthur.github.core.usecases

import com.arthur.github.core.data.network.services.FakeGithubServiceImpl
import com.arthur.github.core.data.network.services.GithubService
import com.arthur.github.core.data.network.util.NetworkUtils
import com.arthur.github.core.data.network.util.onError
import com.arthur.github.core.data.network.util.onSuccess
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TestGetRepos {

    private val fakeGithubService: GithubService = FakeGithubServiceImpl()

    @Test
    fun getReposIsNotEmpty(): Unit = runBlocking {
        val results = fakeGithubService.getRepositories("tmdb")
        results.onSuccess{
            assertTrue(it.items.isNotEmpty())
            assertTrue(it.items.size == 1)
        }
    }

    @Test
    fun getRepos(): Unit = runBlocking {
        val results = fakeGithubService.getRepositories(NetworkUtils.GENERAL_EXCEPTION)
        results.onError {
            assertTrue(it.message == NetworkUtils.GENERAL_EXCEPTION)
        }
    }
}