package com.arthur.github.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthur.github.R
import com.arthur.github.databinding.MainActivityBinding
import com.arthur.github.view.common.extensions.createBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding(R.layout.main_activity)
    }
}