package com.example.patagonianapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.patagonianapp.R
import com.example.patagonianapp.databinding.FragmentSearchBinding
import com.example.patagonianapp.helpers.NetworkHelper.isOnline

class SearchFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            if(binding.tvArtist.text?.isEmpty() == true && binding.tvSongTitle.text?.isEmpty() == true){
                Toast.makeText(requireContext(), getString(R.string.you_must_enter_both), Toast.LENGTH_SHORT).show()
            } else if(binding.tvArtist.text?.isEmpty() == true){
                Toast.makeText(requireContext(), getString(R.string.you_must_enter_artist), Toast.LENGTH_SHORT).show()
            } else if(binding.tvSongTitle.text?.isEmpty() == true){
                Toast.makeText(requireContext(), getString(R.string.you_must_enter_song), Toast.LENGTH_SHORT).show()
            } else{
                navigateToLyrics()
            }
        }

        binding.tvSongTitle.setOnEditorActionListener { _, actionId, _ ->
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
        if(isOnline(requireContext())) {
            val bundle = bundleOf(
                ARTIST to binding.tvArtist.text.toString(),
                TITLE to binding.tvSongTitle.text.toString()
            )
            findNavController().navigate(R.id.action_searchFragment_to_lyricsFragment, bundle)
        } else{
            Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val TITLE = "title"
        const val ARTIST = "artist"
    }
}