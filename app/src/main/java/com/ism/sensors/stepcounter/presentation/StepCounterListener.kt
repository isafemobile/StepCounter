package com.ism.sensors.stepcounter.presentation

interface StepCounterListener {
    fun onStepCountChanged(stepCount: Int)
}