package com.robdir.themoviedb.core

import android.os.Build

fun isLollipopOrLater() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
