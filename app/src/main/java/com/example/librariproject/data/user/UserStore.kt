package com.example.librariproject.data.user

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserStore : ViewModel(){
    private val _username= MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()


    fun  setUsername(username: String){
        Log.d("Login", this.username.value.toString())
        _username.value=username
        Log.d("Hello", this.username.value.toString())
    }
}