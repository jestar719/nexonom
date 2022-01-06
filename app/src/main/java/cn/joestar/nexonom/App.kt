package cn.joestar.nexonom

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DbRepository.initDb(this)
    }
}