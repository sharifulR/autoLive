package com.example.autolive.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.autolive.R
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) = setOnClickListener { func() }


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { replace(frameId, fragment) }

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
    supportFragmentManager.inTransaction { add(frameId, fragment) }

fun AppCompatActivity.removeFragment(fragment: Fragment) =
    supportFragmentManager.inTransaction { remove(fragment) }

fun AppCompatActivity.showFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    show(fragment)
}

fun AppCompatActivity.hideFragment(fragment: Fragment) = supportFragmentManager.inTransaction {
    hide(fragment)
}

fun Long.getNormalDate(): String {
    val formatter = SimpleDateFormat("dd - MMMM - yyyy", Locale.getDefault())
    return formatter.format(this)
}

