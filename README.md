# Wallpaper App

A feature-rich Android wallpaper app that allows users to choose their favorite images and set them as wallpapers on their home screen, lock screen, or both.

## Overview

Wallpaper App lets users explore high-quality images from the Unsplash API. After registering using Firebase, users can select and save their favorite images. While an internet connection is required for browsing and downloading new images, users can access their favorites offline.

## Screenshots

Below are some screenshots from the app:

![Splash_Screen](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot10.png)
![Home Screen](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot1.png)
![Login Screen](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot8.png)
![Register Selection](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot9.png)
![Wallpaper Collection](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot4.png)
![Collection_Discovery](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot5.png)
![Favourites](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot6.png)
![Detail_Screen](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot2.png)
![Bottom_Sheet](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot3.png)
![Settings](https://github.com/SabaDogonadze/Wallpaper/blob/main/Screenshots/Screenshot7.png)

## Features

- **Image Selection:** Browse and choose images from Unsplash.
- **Customizable Wallpapers:** Set images as your home screen, lock screen, or both.
- **User Authentication:** Secure registration and login via Firebase.
- **Offline Access:** View favorite images even without an internet connection.
- **Multi-Language Support:** The app supports two languages (e.g., English and Spanish).
- **Modern Architecture:** Implements MVVM, Clean Architecture, and the Repository Pattern for maintainability.


## Permissions

- **Network Permission:** Required to access the internet for downloading images.
- **Wallpaper Change Permission:** Allows the app to set images as device wallpapers.

## Technologies Used

- **Language:** Kotlin
- **API Integration:** Unsplash API
- **Architecture Patterns:** MVVM, Clean Architecture, Repository Pattern
- **Image Loading:** Glide
- **Networking:** Retrofit, Moshi, OkHttp Logger Interceptor
- **Paging:** Paging 3 for smooth data loading
- **Navigation:** Navigation Graph & Safe Args for seamless in-app navigation
- **Authentication:** Firebase for user registration and login

