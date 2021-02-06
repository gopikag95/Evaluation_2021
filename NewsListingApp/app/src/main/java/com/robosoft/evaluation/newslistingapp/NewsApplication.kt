package com.robosoft.evaluation.newslistingapp

import android.app.Application
import com.robosoft.evaluation.newslistingapp.db.NewsItemDatabase
import com.robosoft.evaluation.newslistingapp.repository.NewsItemDbRepository
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsDbViewModel
import com.robosoft.evaluation.newslistingapp.viewmodel.NewsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))
        bind() from singleton { NewsItemDatabase(instance()) }

        bind() from singleton { NewsItemDbRepository(instance()) }

        bind() from provider {
            NewsViewModelFactory(instance())
        }
    }
}
