package com.himatifunpad.imazine.core.di

import com.himatifunpad.imazine.core.data.AuthRepositoryImpl
import com.himatifunpad.imazine.core.data.PostRepositoryImpl
import com.himatifunpad.imazine.core.domain.repository.AuthRepository
import com.himatifunpad.imazine.core.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun authRepository(repo: AuthRepositoryImpl): AuthRepository

  @Binds
  @Singleton
  abstract fun postRepository(repo: PostRepositoryImpl): PostRepository
}