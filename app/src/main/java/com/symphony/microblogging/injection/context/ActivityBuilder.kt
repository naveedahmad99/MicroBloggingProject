package com.symphony.microblogging.injection.context

import com.symphony.microblogging.ui.authordetails.injection.AuthorDetailsModule
import com.symphony.microblogging.ui.authordetails.presentation.view.activity.AuthorDetailsActivity
import com.symphony.microblogging.ui.authorslist.injection.AuthorsModule
import com.symphony.microblogging.ui.authorslist.presetation.view.activity.AuthorsActivity
import com.symphony.microblogging.ui.postcomments.injection.PostCommentsModule
import com.symphony.microblogging.ui.postcomments.presentation.view.activity.PostCommentsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(AuthorsModule::class)])
    abstract fun bindAuthorsActivity(): AuthorsActivity

    @ContributesAndroidInjector(modules = [(AuthorDetailsModule::class)])
    abstract fun bindAuthorDetailsActivity(): AuthorDetailsActivity

    @ContributesAndroidInjector(modules = [(PostCommentsModule::class)])
    abstract fun bindPostCommentsActivity(): PostCommentsActivity
}

