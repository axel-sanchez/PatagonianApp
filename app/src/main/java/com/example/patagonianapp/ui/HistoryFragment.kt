package com.example.patagonianapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patagonianapp.R
import com.example.patagonianapp.data.room.LyricsDTO
import com.example.patagonianapp.databinding.FragmentHistoryBinding
import com.example.patagonianapp.domain.usecase.GetHistoryUseCase
import com.example.patagonianapp.helpers.hide
import com.example.patagonianapp.helpers.show
import com.example.patagonianapp.ui.adapters.LyricsAdapter
import com.example.patagonianapp.viewmodel.HistoryViewModel
import org.koin.android.ext.android.inject

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    private val getHistoryUseCase: GetHistoryUseCase by inject()
    private val viewModel: HistoryViewModel by viewModels(
        factoryProducer = { HistoryViewModel.HistoryViewModelFactory(getHistoryUseCase) }
    )

    private var fragmentHistoryBinding: FragmentHistoryBinding? = null
    private val binding get() = fragmentHistoryBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHistoryBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLyricsLiveData().observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.emptyState.show()
                binding.list.hide()
            } else {
                binding.emptyState.hide()
                binding.list.show()
                setAdapter(it)
            }
        })
    }

    private fun setAdapter(list: List<LyricsDTO>) {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = LyricsAdapter(list) { itemClick(it) }
        }
    }

    private fun itemClick(lyrics: LyricsDTO) {
        val bundle = bundleOf(
            SearchFragment.ARTIST to lyrics.artist,
            SearchFragment.TITLE to lyrics.title
        )
        findNavController().navigate(R.id.action_historyFragment_to_lyricsFragment, bundle)
    }
}