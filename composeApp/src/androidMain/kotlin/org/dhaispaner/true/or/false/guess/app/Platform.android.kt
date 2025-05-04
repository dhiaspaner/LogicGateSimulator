package org.dhaispaner.`true`.or.`false`.guess.app

import android.os.Build
import commonMain.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

