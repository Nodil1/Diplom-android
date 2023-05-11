package com.nodil.diplom.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _currentPage = MutableLiveData(0)
    val currentPage : LiveData<Int> get() = _currentPage
    init {
        println("INIT")
    }

    fun setPage(page: Int){
        _currentPage.value = page
    }
}