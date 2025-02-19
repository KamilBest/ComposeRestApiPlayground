package com.best.composeRestApiPlayground.di

import com.best.composeRestApiPlayground.usecase.search.data.PostApiService
import com.best.composeRestApiPlayground.usecase.search.data.PostRepository
import com.best.composeRestApiPlayground.usecase.search.data.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
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
}