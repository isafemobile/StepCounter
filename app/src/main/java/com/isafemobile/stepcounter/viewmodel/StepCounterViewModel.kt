package com.isafemobile.stepcounter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.isafemobile.stepcounter.sensor.StepCounterListener
import com.isafemobile.stepcounter.sensor.StepCounterSensor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StepCounterViewModel(application: Application) : AndroidViewModel(application),
    StepCounterListener {
    private val context = application.applicationContext

    private val _stepCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val stepCount = _stepCount.asStateFlow()

    val stepCounterSensor : StepCounterSensor = StepCounterSensor(context, this)
    override fun onStepCountChanged(stepCount: Int) {
        viewModelScope.launch {
            _stepCount.emit(stepCount)
            // Log.d(TAG, "updateStepCount: $stepCount")
        }
    }

    public override fun onCleared() {
        super.onCleared()
        stepCounterSensor.unregister()
    }
}