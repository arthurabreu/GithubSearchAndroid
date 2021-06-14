package com.arthur.github.view.main.search

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthur.github.R
import com.arthur.github.core.data.network.model.Item
import com.arthur.github.databinding.SearchFragmentBinding
import com.arthur.github.view.common.extensions.createSnackBar
import com.arthur.github.view.common.extensions.hideKeyboard
import com.arthur.github.view.common.extensions.viewBinding
import com.arthur.github.view.main.MainViewModel
import com.arthur.github.view.main.RepoListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.search_fragment) {

    private val mainViewModel: MainViewModel by viewModels()
    private val binding by viewBinding(SearchFragmentBinding::bind)

    private lateinit var listAdapter: RepoListAdapter
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListAdapter()
        subscribeObservers()
        initSearchView()
    }

    private fun subscribeObservers() {
        mainViewModel.repositories.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
        mainViewModel.error.observe(viewLifecycleOwner, { error ->
            binding.root.createSnackBar(error, Snackbar.LENGTH_SHORT)
        })
    }

    private fun initSearchView() {
        activity?.apply {
            val searchManager: SearchManager =
                    getSystemService(AppCompatActivity.SEARCH_SERVICE) as SearchManager
            searchView = binding.searchRepos
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(false)
            searchView.isSubmitButtonEnabled = true
        }
        // Enter on computer keyboard or arrow on virtual keyboard
        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        searchPlate.setOnEditorActionListener { v, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                    actionId == EditorInfo.IME_ACTION_SEARCH
            ) {
                val searchQuery = v.text.toString()
                Timber.d("SearchView: (keyboard or arrow) executing search...: $searchQuery")
                doSearch(searchQuery).also { resetUi() }
            }
            true
        }

        // Search button clicked -> inside search view
        val searchButton = searchView.findViewById(R.id.search_go_btn) as View
        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            Timber.d("SearchView: (button) executing search...: $searchQuery")
            doSearch(searchQuery).also { resetUi() }
        }

        // Get the search close button image view
        val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            // Clear the text from EditText view
            searchPlate.setText("")
            // Clear query
            searchView.setQuery("", false)
            searchView.isIconified = true
        }
    }

    private fun doSearch(searchQuery: String) {
        // Dismiss keyboard
        binding.root.hideKeyboard()
        mainViewModel.searchRepos(searchQuery)
    }

    private fun resetUi() {
        searchView.clearFocus()
        binding.repoRecyclerView.smoothScrollToPosition(0)
    }

    private fun setListAdapter() {
        listAdapter = RepoListAdapter(
                onItemClick = {
                    onItemPressed(it)
                }
        )
        binding.repoRecyclerView.adapter = listAdapter

        DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
        ).apply {
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let { setDrawable(it) }
            binding.repoRecyclerView.addItemDecoration(this)
        }
    }

    private fun onItemPressed(item: Item) {
        Timber.d("SearchView: repository url: ${item.htmlUrl}")
        val uris = Uri.parse(item.htmlUrl)
        val b = Bundle().apply { putBoolean("new_window", true) }
        val intents = Intent(Intent.ACTION_VIEW, uris)
        ContextCompat.startActivity(requireContext(), intents, b)
    }
}