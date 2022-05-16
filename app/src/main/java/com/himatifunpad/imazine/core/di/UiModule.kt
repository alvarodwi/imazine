package com.himatifunpad.imazine.core.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import com.himatifunpad.imazine.core.data.remote.NoConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UiModule {
  @Singleton
  @Provides
  fun provideCoilLoader(
    @ApplicationContext context : Context,
    client : OkHttpClient
  ) = ImageLoader.Builder(context)
    .okHttpClient(client)
    .crossfade(true)
    .build()
}