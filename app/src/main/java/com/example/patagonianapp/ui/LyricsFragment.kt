package com.example.patagonianapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.patagonianapp.helpers.Either
import com.example.patagonianapp.helpers.hide
import com.example.patagonianapp.helpers.show
import com.example.patagonianapp.databinding.FragmentLyricsBinding
import com.example.patagonianapp.domain.usecase.GetTheLyricsOfTheSongUseCase
import com.example.patagonianapp.ui.SearchFragment.Companion.ARTIST
import com.example.patagonianapp.ui.SearchFragment.Companion.TITLE
import com.example.patagonianapp.viewmodel.LyricsViewModel
import org.koin.android.ext.android.inject


/**
 * @author Axel Sanchez
 */
class LyricsFragment : Fragment() {

    private val getTheLyricsOfTheSongUseCase: GetTheLyricsOfTheSongUseCase by inject()

    private val viewModel: LyricsViewModel by viewModels(
        factoryProducer = { LyricsViewModel.LyricsViewModelFactory(getTheLyricsOfTheSongUseCase) }
    )

    private var fragmentLyricsBinding: FragmentLyricsBinding? = null
    private val binding get() = fragmentLyricsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentLyricsBinding = FragmentLyricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentLyricsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artist = arguments?.getString(ARTIST)?:""
        val title = arguments?.getString(TITLE)?:""

        binding.titleArtist.text = "$artist - $title"

        viewModel.getLyric(artist, title)

        viewModel.getLyricLiveData().observe(viewLifecycleOwner, { result ->
            if (result is Either.Right) {
                binding.progress.hide()
                binding.lyrics.text = result.r.lyrics
                binding.lyrics.show()
            } else if(result is Either.Left){
                Toast.makeText(requireContext(), result.l.error, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        })
    }
}