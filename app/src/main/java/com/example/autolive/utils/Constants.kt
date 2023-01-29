package com.example.autolive.utils

import android.Manifest

object Constants {
    const val BASE_URL="https://porartable.com/api/"
    const val PREFS_TOKEN_FILE="PREFS_TOKEN_FILE"
    const val USER_TOKEN="USER_TOKEN"


    const val REQUEST_CODE_STORAGE_PERMISSION = 1001
    const val REQUEST_CODE_ALL_PERMISSION = 1002
    const val REQUEST_CODE_CAMERA_AND_AUDIO_PERMISSION = 1003
    const val REQUEST_CODE_SELECT_IMAGE = 2001

    val CAMERA_AND_AUDIO_PERMISSIONS = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    val STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val ALL_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.RECORD_AUDIO
    )

    const val NOTIFICATION_TITLE = "title"
    const val NOTIFICATION_DESCRIPTION= "description"
    const val NOTIFICATION_SENDER_NAME = "senderName"
    const val NOTIFICATION_SENDER_IMAGE = "senderImage"
    const val NOTIFICATION_SENDER_ID = "senderId"
    const val NOTIFICATION_TYPE = "type"
    const val NOTIFICATION_EXTRA = "extra"
    const val NOTIFICATION_KEY_LIVE = "live"
    const val NOTIFICATION_KEY_MESSAGE = "message"
}