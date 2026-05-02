package com.tcastro.data.breeds.utils

object TestUtils {
    fun readJson(fileName: String): String {
        return javaClass.classLoader!!
            .getResourceAsStream(fileName)
            .bufferedReader()
            .use { it.readText() }
    }
}

