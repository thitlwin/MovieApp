package com.thit.movieapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun Context.toast(message:String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

const val imageUrl = "https://image.tmdb.org/t/p/w500/"
const val apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0NzNlMTlhOGU4OWUyYTc1MmJhZmZmMzI5Y2ZjYjY0OCIsInN1YiI6IjYxOTg2YjZiNGY5YTk5MDA2Nzg3MWM1ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.NA4XPsPFdtAfsyEwBNrUdskWR207m1tdpWpjOrK1Mwc"