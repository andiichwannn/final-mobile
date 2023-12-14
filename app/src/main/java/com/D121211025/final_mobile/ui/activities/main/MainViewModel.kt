package com.D121211025.final_mobile.ui.activities.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.D121211025.final_mobile.MyApplication
import com.D121211025.final_mobile.data.models.Result
import com.D121211025.final_mobile.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MainUiState {
    data class Success(val movies: List<Result>) : MainUiState
    object Error : MainUiState
    object Loading : MainUiState
}

class MainViewModel(private val moviesRepository: MovieRepository): ViewModel() {

    // Initial state
    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    fun getMovies() = viewModelScope.launch {
        mainUiState = MainUiState.Loading
        try {
            val result = moviesRepository.getMovies()
            mainUiState = MainUiState.Success(result.results.orEmpty())
        } catch (e: IOException) {
            mainUiState = MainUiState.Error
        }
    }

    // Yang pertama dipanggil ketika program dijalankan
    init {
        getMovies()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)
                val moviesRepository = application.container.movieRepository
                MainViewModel(moviesRepository)
            }
        }
    }
}