package com.example.jetpackpaging.di

import android.content.Context
import com.example.jetpackpaging.data.*
import com.example.jetpackpaging.data.dao.ArticleDao
import com.example.jetpackpaging.data.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun providerApiService(): ApiService = RetrofitClient.createService()


    @Singleton
    @Provides
    fun providerDataBase(@ApplicationContext context: Context): AppDataBase =
        AppDataBase.getInstance(context)


    @Singleton
    @Provides
    fun providerArticleDao(dataBase: AppDataBase): ArticleDao = dataBase.articleDao()


    @Singleton
    @Provides
    fun providerRemoteKeyDao(dataBase: AppDataBase): RemoteKeysDao = dataBase.remoteKeysDao()

}