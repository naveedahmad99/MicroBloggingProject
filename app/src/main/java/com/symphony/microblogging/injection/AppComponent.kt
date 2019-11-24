package com.symphony.microblogging.injection

import android.app.Application
import com.symphony.microblogging.App
import com.symphony.microblogging.injection.context.ActivityBuilder
import com.symphony.microblogging.injection.modules.AuthorsDatabaseModule
import com.symphony.microblogging.injection.modules.AppModule
import com.symphony.microblogging.injection.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        AuthorsDatabaseModule::class,
        NetworkModule::class]
)
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}