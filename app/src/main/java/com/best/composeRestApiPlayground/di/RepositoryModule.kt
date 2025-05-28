package com.best.composeRestApiPlayground.di

import com.best.composeRestApiPlayground.usecase.async.data.AsyncApiService
import com.best.composeRestApiPlayground.usecase.async.data.AsyncRepository
import com.best.composeRestApiPlayground.usecase.async.data.AsyncRepositoryImpl
import com.best.composeRestApiPlayground.usecase.search.data.PostApiService
import com.best.composeRestApiPlayground.usecase.search.data.PostRepository
import com.best.composeRestApiPlayground.usecase.search.data.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostApiService(client: HttpClient): PostApiService {
        return PostApiService(client)
    }

    @Provides
    @Singleton
    fun providePostRepository(apiService: PostApiService): PostRepository {
        return PostRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAsyncApiService(client: HttpClient): AsyncApiService {
        return AsyncApiService(client)
    }

    @Provides
    @Singleton
    fun provideAsyncRepository(apiService: AsyncApiService): AsyncRepository {
        return AsyncRepositoryImpl(apiService)
    }
}