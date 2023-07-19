package com.finaltask.meatq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    val image : MutableLiveData<String> = MutableLiveData("empty")

    fun changeImage(image: String){
        this.image.postValue(image)
    }
}