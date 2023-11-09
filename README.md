## StepCounter App:
This app is designed to track the number of steps you take, using **Sensor.TYPE_STEP_COUNTER** . When the app starts, it also starts a timer right away. As you walk, the app will show the number of steps you've taken and the time recorded by the timer on the screen.

 **Device Information**:
| | |
|---|---|
| **Brand:** | isafemobile |
| **Name:** | IS_SW1 |
| **Android Version:** | 8.1.0 |
| **SDK Version:** | 27 |
| **OS** | AOSP |
 ----
## Problem Describtion:
When the watch is restarted, it doesn't immediately begin transmitting data about steps taken. There's a noticeable lag—sometimes over a minute—before it starts recording the number of steps. However, after this initial delay, it updates the app with all the steps taken during that time. For example, in a demonstration video, the app shows a sudden jump to 668 steps at the very beginning, suggesting that the watch is counting steps but not sending the information in real-time for some reason.

[Demonstration Video](https://www.youtube.com/watch?v=5bo2tUn3BNs)

 ----
## 📋 Analyze & Observations 

>🛠️ Install ADB Wi-Fi to ease debugging of the step counter sensor on smartwatches without the need for a dock station that often disconnects upon movement. [Check it out here!](https://plugins.jetbrains.com/plugin/14969-adb-wi-fi)
 
Debug steps are taked to observe sensor behaviour and the Apps reponses. the App always respond to any sensor data comes from sensor-device [check step 2.] 

 
## step 1. Enable debuggable-mode:

1. Press the **Power-Button**, scroll down, and choose **Settings**
2. Scroll down to **About Device**
3. Locate **SN No.** and press it 10 times continuously
4. Press the **Back-Button**
5. Press and hold the **Settings** label until "TestTools" opens, then activate the toggle for **Debuggable**
   

 
## step 2. Monitor sensor data of step counter
 
1. ```adb shell```
2. ```getevent -i```
3. noting the device name -**sc** stands for **s**tep-**c**ounter :
 "GR5515-**sc** device 9: /dev/input/event4"
4. ``` getevent /dev/input/event4 ```
5. Shake the device to initiate the step counter sensor due to movement and check the output on the terminal

 
## step 3. Check who use sensorservice
  
```adb shell dumpsys sensorservice```
shows 2 active connections:
It is worth mentioning that when the sensor is properly started upon reboot, both apps are able to read the sensor data for counting steps without contradiction.
 - com.ism.sensors.stepcounter.presentation.StepCounterSensor [this Repository]
 - com.sikey.commonservice.service.sensor.SensorListener 

## step 4. Check when sensor-event is being called

 ```adb logcat | Select-String "sensor"```
logs shows on WatchShaking the following SENSOR_EVENT:
- 11-07 13:13:09.020  1099  1460 D LocalSocketMessageParser: SENSOR_EVENT

while on the another terminal
$``` getevent /dev/input/event4 ``` send no data. 

## step 5. Hardware details of step counter sensor

$ ````adb shell cat /proc/bus/input/devices````
Terminal output for Hardware Information for *Sensor.TYPE_STEP_COUNTER* device :
-  I: Bus=0000 Vendor=0000 Product=0000 Version=0000
N: Name="GR5515-sc"
P: Phys=
S: Sysfs=/devices/soc/78b7000.spi/spi_master/spi32766/spi32766.0/input/input4
U: Uniq=
H: Handlers=event4
B: PROP=0
B: EV=9
B: ABS=100 8

[Manufacturer Documentation](https://invensense.tdk.com/wp-content/uploads/2023/05/an-000271-icm-42607x-icm-42670x-apex-motion-functions-description-and-usage.pdf)

 ----
 
## 🖼️ Screenshots

<img src="/demo/screenshot_379.png" width="250"/> 

## ⚙️Technologies

- Kotlin KTX
- MVVM
- Jetpack Compose 
- Coroutines




  

