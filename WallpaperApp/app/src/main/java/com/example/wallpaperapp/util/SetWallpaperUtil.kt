package com.example.wallpaperapp.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log


/* context: Used to access system services (like the WallpaperManager).
*  bitmap: The image that will be set as the wallpaper. */


class SetWallpaperUtil(private val context: Context, private val bitmap: Bitmap) {
    fun setWallpaper(wallpaperType: WallpaperTypes) {
        val wallpaperManager = WallpaperManager.getInstance(context)   // Retrieves the system service responsible for setting wallpapers.
        try {
            when (wallpaperType) {
                WallpaperTypes.HOME -> {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)  //Sets the wallpaper for the home screen using the system flag.
                }

                WallpaperTypes.LOCK -> {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)  //Sets the wallpaper for the lock screen using the lock flag.
                }

                WallpaperTypes.HOME_LOCK -> {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)  // Sets For Both
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
            }
        } catch (e: Exception) {
            Log.e("SetWallpaperUtil", "Error setting wallpaper", e)
        }
    }
}
