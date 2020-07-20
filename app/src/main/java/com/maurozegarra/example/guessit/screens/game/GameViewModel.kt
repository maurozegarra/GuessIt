package com.maurozegarra.example.guessit.screens.game

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    init {
        Log.i(TAG, "Called: GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "Called: GameViewModel destroyed!")
    }
}
