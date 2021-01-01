package com.example.mvvm.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mvvm.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mvvm.data.entities.Character
import com.example.mvvm.utils.Resource
import kotlinx.android.synthetic.main.characters_fragment.*
import kotlinx.android.synthetic.main.characters_fragment.view.*
import timber.log.Timber

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()
    private var characters: List<Character> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.characters_fragment, container, false)
        setupObservers()

        viewModel.getCharacters()
        view.refresh.setOnClickListener {
            viewModel.getCharacters()
        }

        return view
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            it?.let { charactersResult ->
                when (charactersResult.status) {

                    Resource.Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE

                        if (!charactersResult.data.isNullOrEmpty())
                            characters = charactersResult.data

                        Timber.i(characters.toString());
                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(activity, charactersResult.message, Toast.LENGTH_SHORT).show()
                        Timber.i("Error!!!")
                    }

                    Resource.Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                        Timber.i("Loading...")
                    }
                }
            }
        })
    }
}