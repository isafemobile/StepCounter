package com.ism.sensors.stepcounter.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.ism.sensors.stepcounter.presentation.Constants.TAG

class StepCounterSensor(context: Context, private val listener: StepCounterListener) :
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
                Log.d(TAG, "onSensorChanged: ${it.values[0]}")
                stepCount += it.values[0].toInt()
                Log.d(TAG, "onSensorChanged Debounced: $stepCount")
                listener.onStepCountChanged(stepCount)
            }
        }
    }
}


