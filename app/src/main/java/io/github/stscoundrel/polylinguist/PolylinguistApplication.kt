package io.github.stscoundrel.polylinguist


import android.app.Application
import io.github.stscoundrel.polylinguist.di.AppContainer
import io.github.stscoundrel.polylinguist.di.DefaultAppContainer

class PolylinguistApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}