package com.example.branch_project.di

import com.example.branch_project.data.interceptor.HeaderInterceptor
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    @Singleton
    @Provides
    fun providesHeaderInterceptor(): HeaderInterceptor =
        HeaderInterceptor()
}