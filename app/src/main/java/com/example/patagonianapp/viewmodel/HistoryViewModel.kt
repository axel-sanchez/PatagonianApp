package com.example.patagonianapp.viewmodel

/**
 * @author Axel Sanchez
 */
import androidx.lifecycle.*
import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.launch

/**
 * View model de [MyFragment]
 * @author Axel Sanchez
 */
class HistoryViewModel(private val getHistoryUseCase: GetHistoryUseCase) : ViewModel() {

    private val listData: MutableLiveData<List<LyricsDTO>> = MutableLiveData<List<LyricsDTO>>()

    private fun setListData(lyricsList: List<LyricsDTO>) {
        listData.postValue(lyricsList)
    }

    fun getLyrics() {
        viewModelScope.launch {
            setListData(getHistoryUseCase.call())
        }
    }

    fun getLyricsLiveData(): LiveData<List<LyricsDTO>> {
        return listData
    }

    class HistoryViewModelFactory(private val getHistoryUseCase: GetHistoryUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(GetHistoryUseCase::class.java).newInstance(getHistoryUseCase)
        }
    }
}