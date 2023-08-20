package com.example.branch_project.di

import com.example.branch_project.commons.createOkHttpClient
import com.example.branch_project.commons.createWebService
import com.example.branch_project.data.interceptor.HeaderInterceptor
import com.example.branch_project.data.remote.BranchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient =
        createOkHttpClient(headerInterceptor)

    @Singleton
    @Provides
    fun providesBranchApi(okHttpClient: OkHttpClient): BranchService =
        createWebService(okHttpClient, BranchService.BASE_URL)

}