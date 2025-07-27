package com.example.museumbatikv1
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    // StateFlow untuk menyimpan status loading. Awalnya true (sedang loading).
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        // Ini adalah blok untuk mensimulasikan proses loading data
        // seperti mengambil data dari internet atau database.
        viewModelScope.launch {
            delay(2000) // Tunda selama 2 detik untuk simulasi
            _isLoading.value = false // Setelah selesai, ubah status jadi false
        }
    }
}