package com.himatifunpad.imazine.core.di

import com.himatifunpad.imazine.core.data.AuthRepositoryImpl
import com.himatifunpad.imazine.core.domain.repository.AuthRepository
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
  abstract fun authRepository(repo : AuthRepositoryImpl) : AuthRepository
}