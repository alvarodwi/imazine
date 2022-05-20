package com.himatifunpad.imazine.core.di

import coil.ImageLoader
import com.himatifunpad.imazine.core.data.local.DataStoreManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PrefsEntryPoint {
  fun prefs(): DataStoreManager
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ImageLoaderEntryPoint {
  fun coilLoader(): ImageLoader
}