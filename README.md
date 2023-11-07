## 📋 Debug and Analyze 

> Warning: Debug mode must be acitvated
install ADB Wi-Fi to ease -debugging sensors on smartwatch without need for the dockstation that often disconnect upon moves. https://plugins.jetbrains.com/plugin/14969-adb-wi-fi 

 ----
Enable debuggable-mode on SW1.1:

1. Press the **Power-Button**, scroll down, and choose **Settings**
2. Scroll down to **About Device**
3. Locate **SN No.** and press it 10 times continuously
4. Press the **Back-Button**
5. Press and hold the **Settings** label until "TestTools" opens, then activate the toggle for **Debuggable**
   
using Android Debug Bridge to check the devices on the watch including all type of sensors: 

 ----
1. ```adb shell```
2. ```getevent -i```
3. noting the device name -**sc** stands for **s**tep-**c**ounter :
 "GR5515-**sc** device 9: /dev/input/event4"
4. ``` getevent /dev/input/event4 ```
5. Shake the device to initiate the step counter sensor due to movement and check the output on the terminal

 ----
 
## 🖼️ Screenshots

<img src="/demo/screenshot_379.png" width="250"/> 

## ⚙️Technologies

- Kotlin KTX
- MVVM
- Jetpack Compose 
- Coroutines




  

