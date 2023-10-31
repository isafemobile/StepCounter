package com.ism.sensors.stepcounter.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StepCounterViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _stepCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    val stepCounterSensor = StepCounterSensor(context) {
        updateStepCount(it)
    }

    fun updateStepCount(stepCount: Int) {
        viewModelScope.launch {
            _stepCount.emit(stepCount)
        }
       // Log.d(TAG, "updateStepCount: $stepCount")
    }

    public override fun onCleared() {
        super.onCleared()
        stepCounterSensor.unregister()
    }
}