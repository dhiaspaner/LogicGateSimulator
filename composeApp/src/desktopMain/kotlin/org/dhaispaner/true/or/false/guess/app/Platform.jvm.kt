package org.dhaispaner.`true`.or.`false`.guess.app

import commonMain.Platform

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

