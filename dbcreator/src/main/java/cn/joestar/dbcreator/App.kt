package cn.joestar.dbcreator

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DbManager.init(this)
    }
}