package com.example.newsproject.utils.extensions

import android.content.res.Resources

val Number.pxInt get() = (this.toFloat() / Resources.getSystem().displayMetrics.density).toInt()