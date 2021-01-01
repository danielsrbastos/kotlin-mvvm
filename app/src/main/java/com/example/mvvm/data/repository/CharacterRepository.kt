package com.example.mvvm.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.entities.Character
import com.example.mvvm.data.local.CharacterDao
import com.example.mvvm.data.remote.CharacterRemoteDataSource
import com.example.mvvm.utils.Resource
import com.example.mvvm.utils.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    suspend fun getCharacters(mutableLiveData: MutableLiveData<Resource<List<Character>>>) =
        performGetOperation(mutableLiveData, localDataSource.getAllCharacters(),
            networkCall = { remoteDataSource.getCharacters() },
            saveCallResult = { localDataSource.insertAll(it.results) }
        )
}