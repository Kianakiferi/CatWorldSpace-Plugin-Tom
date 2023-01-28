package com.catworldspace.plugin.system.models

abstract class Singleton<out T>(creator: () -> T) {
    @Volatile
    private var instance: T? = null

    private var creator: (() -> T)? = creator

    fun getInstance(): T =
        instance ?: synchronized(this) {
            instance ?: creator!!().apply { instance = this }
        }

}