package com.symphony.microblogging

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.symphony.microblogging.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initStetho()
//        initLeakCanary()
    }

    private fun initStetho() {
        if (BuildConfig.QA) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
            )
        }
    }

    private fun initDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

//    private fun initLeakCanary() {
//        LeakCanary.install(this)
//    }
}