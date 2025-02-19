package com.best.composeRestApiPlayground.di

import com.best.composeRestApiPlayground.usecase.search.SearchPostsUseCase
import com.best.composeRestApiPlayground.usecase.search.SearchPostsUseCaseImpl
import com.best.composeRestApiPlayground.usecase.search.data.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSearchPostsUseCase(repository: PostRepository): SearchPostsUseCase {
        return SearchPostsUseCaseImpl(repository)
    }
}