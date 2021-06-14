package com.arthur.github.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.github.core.data.network.model.Item
import com.arthur.github.core.data.network.services.GithubService
import com.arthur.github.core.data.network.util.onError
import com.arthur.github.core.data.network.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class MainViewModel
@Inject
constructor(
        private val githubService: GithubService
) : ViewModel() {

    private val _repositories = MutableLiveData<List<Item>>()
    val repositories: LiveData<List<Item>> = _repositories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun searchRepos(query: String) {
        viewModelScope.launch{
            githubService.getRepositories(query)
                    .onSuccess { repositories ->
                        _repositories.value = repositories.items
                    }
                    .onError { error ->
                        _error.value = error.message
                    }
        }
    }
}