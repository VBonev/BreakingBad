package com.today.breakingbad.overview

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.today.breakingbad.R
import kotlinx.android.synthetic.main.fragment_characters_overview.*

class CharactersOverviewFragment : Fragment(R.layout.fragment_characters_overview) {

    private val viewModel: CharactersOverviewViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characters_recycler_view.adapter = charactersAdapter
        setUpSeasonSpinner()
        setUpSearchView()
        viewModel.fetchCharacters()
        viewModel.charactersList.observe(viewLifecycleOwner, {
            charactersAdapter.charactersList = it
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun setUpSeasonSpinner() {
        val spinner = toolbar.menu.findItem(R.id.action_spinner).actionView as Spinner
        val seasons = mutableListOf<String>()
        seasons.add("All Seasons")
        for (i in 1..5) {
            seasons.add(String.format(getString(R.string.spinner_label), i))
        }
        toolbar.title = "All Seasons"
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, seasons)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                viewModel.filterBySeason(position)
                toolbar.title = seasons[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun setUpSearchView() {
        val searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchByName(newText)
                return true
            }
        })
    }
}