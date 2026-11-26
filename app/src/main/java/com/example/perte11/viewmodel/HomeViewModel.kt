package com.example.perte11.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perte11.modeldata.DataSiswa
import com.example.perte11.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa>) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var statusUiSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            statusUiSiswa = StatusUiSiswa.Loading
            statusUiSiswa = try {
                StatusUiSiswa.Success(repositoryDataSiswa.getDataSiswa())
            } catch (e: Exception) {
                Log.e("API_ERROR", "${e.javaClass.simpleName}: ${e.message}", e)
                StatusUiSiswa.Error
            }
        }
    }
}