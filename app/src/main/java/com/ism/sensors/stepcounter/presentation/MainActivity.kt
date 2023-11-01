package com.ism.sensors.stepcounter.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var stepCounterViewModel: StepCounterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reformViewModel()

        setContent {
            val stepCount by stepCounterViewModel.stepCount.collectAsStateWithLifecycle()
            val stopWatchViewModel = viewModel<StopWatchViewModel>()
            val timerState by stopWatchViewModel.timerState.collectAsStateWithLifecycle()
            val stopWatchText by stopWatchViewModel.stopWatchText.collectAsStateWithLifecycle()
            Scaffold(
                timeText = {
                    TimeText(
                        timeTextStyle = TimeTextDefaults.timeTextStyle(
                            fontSize = 10.sp
                        )
                    )
                },
                vignette = {
                    Vignette(vignettePosition = VignettePosition.TopAndBottom)
                }
            ) {
                StopWatch(
                    state = timerState,
                    timer = stopWatchText,
                    onToggleRunning = stopWatchViewModel::toggleIsRunning,
                    onReset = stopWatchViewModel::resetTimer,
                    modifier = Modifier.fillMaxSize(),
                    stepCount = stepCount,
                )
            }
        }
    }

    private fun reformViewModel() {
        val factory = StepCounterViewModelFactory(application)
        stepCounterViewModel =
            ViewModelProvider(this, factory)[StepCounterViewModel::class.java]
        lifecycleScope.launch {
            stepCounterViewModel.stepCount.collect {
                Log.d(Constants.TAG, "trace Step Count: $it")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stepCounterViewModel.onCleared()
    }
}

@Composable
private fun StopWatch(
    state: TimerState,
    timer: String,
    stepCount: Int,
    onToggleRunning: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onToggleRunning() // start timer
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedSensoringText()
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stepCount.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color(0xFFDE2921)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = timer,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onToggleRunning) {
                Icon(
                    imageVector = if (state == TimerState.RUNNING) {
                        Icons.Default.Pause
                    } else {
                        Icons.Default.PlayArrow
                    },
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onReset,
                enabled = state != TimerState.RESET,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Stop,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun AnimatedSensoringText() {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Yellow,
        Color.Cyan,
        Color.Blue,
        Color.Magenta,
        Color.DarkGray
    )
    val currentColorIndex = remember { mutableStateOf(0) }
    val animatedColor by animateColorAsState(targetValue = colors[currentColorIndex.value])

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // change color every second
            currentColorIndex.value = (currentColorIndex.value + 1) % colors.size
        }
    }

    Text(
        text = "Step Counter is Sensing...",
        //text = stringResource(id = R.string.step_counter_text), //TODO add string resource cant be resolved!
        color = animatedColor,
        fontSize = 12.sp,
        fontStyle = FontStyle.Italic,
        textAlign = TextAlign.Center
    )
}
