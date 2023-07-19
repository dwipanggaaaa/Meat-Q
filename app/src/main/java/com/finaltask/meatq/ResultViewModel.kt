package com.finaltask.meatq

import androidx.lifecycle.ViewModel
import java.io.File

class ResultViewModel(
    private val repository: Repository = Repository()
): ViewModel() {

    suspend fun uploadImage(file: File): Response{
        return repository.uploadImage(file)
    }
}