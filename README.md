# 📈🚶‍♂️ Step Counter 

This repository contains a step counter designed specifically for the wear OS of i.safe MOBILE watches. Not only does it capture steps using the step counter sensor, but it also offers a stopwatch feature.
Important to note, resetting the stopwatch does not affect or reset the step counting. However, the step counter does reset upon starting the application.


## 📋 Debug and Analyze 
using Android Debug Bridge# to check the devices on the device including all type of sensors: 

1. ```adb shell```
2. ```getevent -i```
3. noting the device name -sc stands for step-counter :
 "GR5515-sc” device 9: /dev/input/event4"
4. ``` getevent /dev/input/event4 ```
5. Shake the device to initiate the step counter sensor due to movement and check the output on the terminal

⚠️ Note: While shaking the device can initiate the step counter sensor, it's essential to understand that the step-counter sensor is optimized to capture movements from activities like walking or running. These activities have more distinct patterns compared to random shaking. For more accurate testing, it's recommended to walk or run rather than shake the device.

## 🖼️ Screenshots

<img src="/demo/screenshot_10.png" width="250"/> 

## ⚙️Technologies

- Kotlin KTX
- MVVM
- Jetpack Compose 
- Coroutines




  

