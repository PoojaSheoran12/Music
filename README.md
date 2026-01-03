

# 🎵 Music Player App (Kotlin Multiplatform)

A music player application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, showcasing Android fundamentals, clean architecture, offline support, and media playback.

---

## 🚀 Key Features

* Fetches music tracks from a remote API (Jamendo)
* Displays track list with:

  * Title
  * Artist
  * Duration
  * Album artwork
* Pagination with **10 tracks per request**
* Loading and error states handled gracefully
* Sorting options:

  * Name (A–Z)
  * Duration (shortest to longest)
* Audio playback using **Android MediaPlayer**
* Playback controls:

  * Play / Pause
  * Next / Previous track
  * Seek bar with current position and total duration
* **Offline-first support**:
  * Track metadata cached locally using SQLDelight
* Mini Player and Full Player screens
* Polished dark-themed UI
* Proper MediaPlayer lifecycle handling
* Clean **MVVM architecture** with StateFlow & Coroutines

---

## 🛠 Tech Stack

* Kotlin Multiplatform (KMP)
* Compose Multiplatform (CMP)
* MVVM architecture
* Ktor Client
* SQLDelight
* Coroutines & StateFlow
* Koin (DI)
* Android MediaPlayer
* Coil (Android)

---


## 🌐 API Used

### 🎶 Jamendo API

**Why Jamendo?**

* Free and public music API
* Provides audio streaming URLs and artwork
* Suitable for demo and learning purposes
* Simple integration without OAuth complexity

---

## 🔑 API Configuration

To run the app, you need a **Jamendo client ID**.

### Steps:

1. Visit: [https://www.jamendo.com/start](https://www.jamendo.com/start)
2. Create a free account
3. Generate a **client_id**
4. Add it to your project using Gradle configuration

Example (`gradle.properties`):

```properties
JAMENDO_CLIENT_ID=your_client_id_here
```

The client ID is injected at build time and **not hardcoded in source code**.

---

## 🚀 How to Run the App

### Option 1: Android Studio

1. Clone the repository:

   ```bash
   git clone <https://github.com/PoojaSheoran12/Music.git>
   ```
2. Open in **Android Studio**
3. Sync Gradle
4. Select Android configuration
5. Run on emulator or physical device

---

### Option 2: Install APK

* Download the APK from the repository
* Install it on an Android device

📁 APK location:

```
/apk/MusicPlayer-debug.apk
```

---


## 🧠 Assumptions & Design Choices

* Only **10 tracks per API request** to control bandwidth and paging
* Android is the primary playback platform
* Focus on robustness, readability, and maintainability

---

## ✅ Assignment Coverage

| Requirement            | Status |
| ---------------------- | ------ |
| Kotlin & KMP           | ✅      |
| MVVM Architecture      | ✅      |
| API Integration        | ✅      |
| Ktor Networking        | ✅      |
| JSON Parsing           | ✅      |
| MediaPlayer            | ✅      |
| Loading & Error States | ✅      |
| Sorting                | ✅      |
| Offline Caching        | ✅      |
| Offline Playback       | ✅      |
| Polished UI            | ✅      |

---

