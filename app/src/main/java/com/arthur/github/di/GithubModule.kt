package com.arthur.github.di

import com.arthur.github.core.data.network.api.GithubApi
import com.arthur.github.core.data.network.services.GithubService
import com.arthur.github.core.data.network.services.GithubServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubModule {

    @Singleton
    @Provides
    fun provideGithubApi(
            retrofitBuilder: Retrofit.Builder
    ): GithubApi {
        return retrofitBuilder.build().create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun providesGithubService(
            githubApi: GithubApi
    ): GithubService {
        return GithubServiceImpl(githubApi)
    }
}