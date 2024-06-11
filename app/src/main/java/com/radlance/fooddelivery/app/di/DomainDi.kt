package com.radlance.fooddelivery.app.di

import com.radlance.fooddelivery.data.repository.UserRepositoryImpl
import com.radlance.fooddelivery.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainDi {
    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}