package com.rbb.bluefetchclient.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsAuthRepository(
        dogImagesRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
