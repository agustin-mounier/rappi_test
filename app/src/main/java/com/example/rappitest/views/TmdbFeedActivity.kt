package com.example.rappitest.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.rappitest.viewmodels.TmdbFeedViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class TmdbFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TmdbFeedViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TmdbFeedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.fetchMostPopular()
    }
}