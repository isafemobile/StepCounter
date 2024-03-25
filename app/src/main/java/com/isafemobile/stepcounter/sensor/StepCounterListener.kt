package com.isafemobile.stepcounter.sensor

interface StepCounterListener {
    fun onStepCountChanged(stepCount: Int)
}