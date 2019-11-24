package com.symphony.microblogging.injection.modules

import android.content.Context
import androidx.room.Room
import com.symphony.microblogging.data.local.dao.AuthorDao
import com.symphony.microblogging.data.local.database.AuthorsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthorsDatabaseModule {
    @Singleton
    @Provides
    fun provideAuthorsDatabase(context: Context): AuthorsDatabase =
        Room.databaseBuilder(
            context,
            AuthorsDatabase::class.java, AuthorsDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideAuthorDao(authorsDatabase: AuthorsDatabase): AuthorDao {
        return authorsDatabase.AuthorDao()
    }
}