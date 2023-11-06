package com.ism.sensors.stepcounter.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class StepCounterSensor(context: Context, private val OnStepCounterChanged: (Int) -> Unit) :
    SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    private var stepCount: Int = 0

    // For debouncing
    private val debounceTime: Long = 1000 // in milliseconds
    private var lastTime: Long = 0
    private var lastStepCount: Int = 0

    init {
        register()
    }

    private fun register() {
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle changes in sensor accuracy
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val currentTime = System.currentTimeMillis()
                OnStepCounterChanged(stepCount)
                // Debounce check
                //TODO ignore the debounce check at the moment in order to sense all sensor events
                /*if (abs(currentTime - lastTime) > debounceTime) {
                    val stepsSinceLast = it.values[0].toInt() - lastStepCount
                    if (stepsSinceLast > 0) {
                        stepCount += stepsSinceLast
                        OnStepCounterChanged(stepCount)
                        lastStepCount = it.values[0].toInt()
                        lastTime = currentTime
                    }*/
            }
        }
    }
}

