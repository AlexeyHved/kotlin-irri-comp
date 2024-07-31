package com.github.alexeyhved.irricomp.di

import com.github.alexeyhved.irricomp.data.RepoIMpl
import com.github.alexeyhved.irricomp.domain.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun repo() : Repo {
        return RepoIMpl()
    }
}