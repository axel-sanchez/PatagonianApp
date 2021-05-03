package com.example.patagonianapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.patagonianapp.R
import com.example.patagonianapp.databinding.FragmentSearchBinding
import com.example.patagonianapp.domain.usecase.GetHistoryUseCase
import com.example.patagonianapp.helpers.NetworkHelper.isOnline
import com.example.patagonianapp.helpers.hide
import com.example.patagonianapp.helpers.show
import com.example.patagonianapp.viewmodel.HistoryViewModel
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {

    private val getHistoryUseCase: GetHistoryUseCase by inject()
    private val viewModel: HistoryViewModel by viewModels(
        factoryProducer = { HistoryViewModel.HistoryViewModelFactory(getHistoryUseCase) }
    )

    private var fragmentSearchBinding: FragmentSearchBinding? = null
    private val binding get() = fragmentSearchBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSearchBinding = null
    }

    override fun onResume() {
        super.onResume()
        binding.etArtist.setText("")
        binding.etSongTitle.setText("")
        viewModel.getLyrics()
        viewModel.getLyricsLiveData().observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                binding.lastSearch.show()
                binding.lastSearch.text = "Last Search: ${it.first().artist} - ${it.first().title}"
            } else{
                binding.lastSearch.hide()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_historyFragment)
        }

        binding.btnSearch.setOnClickListener {
            navigateToLyrics()
        }

        binding.etSongTitle.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    navigateToLyrics()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToLyrics() {
        if(binding.etArtist.text?.isEmpty() == true && binding.etSongTitle.text?.isEmpty() == true){
            Toast.makeText(requireContext(), getString(R.string.you_must_enter_both), Toast.LENGTH_SHORT).show()
        } else if(binding.etArtist.text?.isEmpty() == true){
            Toast.makeText(requireContext(), getString(R.string.you_must_enter_artist), Toast.LENGTH_SHORT).show()
        } else if(binding.etSongTitle.text?.isEmpty() == true){
            Toast.makeText(requireContext(), getString(R.string.you_must_enter_song), Toast.LENGTH_SHORT).show()
        } else{
            if (isOnline(requireContext())) {
                val bundle = bundleOf(
                    ARTIST to binding.etArtist.text.toString(),
                    TITLE to binding.etSongTitle.text.toString()
                )
                findNavController().navigate(R.id.action_searchFragment_to_lyricsFragment, bundle)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        const val TITLE = "title"
        const val ARTIST = "artist"
    }
}