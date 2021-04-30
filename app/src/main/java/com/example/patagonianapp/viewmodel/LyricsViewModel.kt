package com.example.patagonianapp.viewmodel

import androidx.lifecycle.*
import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.data.model.Result
import com.example.patagonianapp.data.source.ApiError
import com.example.patagonianapp.domain.usecase.GetTheLyricsOfTheSongUseCase
import kotlinx.coroutines.launch

/**
 * View model de [MyFragment]
 * @author Axel Sanchez
 */
class LyricsViewModel(private val getTheLyricsOfTheSongUseCase: GetTheLyricsOfTheSongUseCase): ViewModel() {

    private val listData = MutableLiveData<Either<ApiError, Result>>()

    private fun setListData(result: Either<ApiError, Result>) {
        listData.postValue(result)
    }

    fun getLyric(artist: String, title: String) {
        viewModelScope.launch {
            setListData(getTheLyricsOfTheSongUseCase.call(artist, title))
        }
    }

    fun getLyricLiveData(): LiveData<Either<ApiError, Result>> {
        return listData
    }

    class LyricsViewModelFactory(private val getTheLyricsOfTheSongUseCase: GetTheLyricsOfTheSongUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetTheLyricsOfTheSongUseCase::class.java).newInstance(getTheLyricsOfTheSongUseCase)
        }
    }
}