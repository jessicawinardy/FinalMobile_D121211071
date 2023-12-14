package com.example.tugasfinal_d121211071.ui.activities.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasfinal_d121211071.MyApplication
import com.example.tugasfinal_d121211071.data.models.Disney
import com.example.tugasfinal_d121211071.data.repository.DisneyRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MainUiState {
    data class Success(val disney: List<Disney>) : MainUiState
    object Error : MainUiState
    object Loading : MainUiState
}

class MainViewModel(private val disneyRepository: DisneyRepository): ViewModel() {

    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    fun getDisney() = viewModelScope.launch {
        mainUiState = MainUiState.Loading
        try {
            val result = disneyRepository.getDisney()
            Log.d("MainViewModel", "getDisney: ${result.size}")
            mainUiState = MainUiState.Success(result.orEmpty())
        } catch (e: IOException) {
            Log.d("MainViewMode", "getDisney error: ${e.message}")
            mainUiState = MainUiState.Error
        }
    }

    init {
        getDisney()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val newsRepository = application.container.disneyRepository
                MainViewModel(newsRepository)
            }
        }
    }

}