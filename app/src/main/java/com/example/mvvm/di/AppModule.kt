package com.example.mvvm.di

import android.content.Context
import com.example.mvvm.data.local.AppDatabase
import com.example.mvvm.data.local.CharacterDao
import com.example.mvvm.data.remote.CharacterRemoteDataSource
import com.example.mvvm.data.remote.CharacterService
import com.example.mvvm.data.repository.CharacterRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: CharacterService) =
        CharacterRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: CharacterRemoteDataSource,
        localDataSource: CharacterDao
    ) =
        CharacterRepository(remoteDataSource, localDataSource)
}