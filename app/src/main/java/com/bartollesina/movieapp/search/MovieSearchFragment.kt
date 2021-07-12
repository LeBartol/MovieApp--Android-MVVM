package com.bartollesina.movieapp.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartollesina.movieapp.adapter.MovieRecyclerAdapter
import com.bartollesina.movieapp.databinding.FragmentMovieSearchBinding
import com.bartollesina.movieapp.utils.nonNullObserve
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
class MovieSearchFragment : Fragment() {
    private var _binding: FragmentMovieSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieSearchVM by viewModel()
    val adapter by lazy { MovieRecyclerAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackClick()
        setEditText()
        setUpRecyclerView()
        setObserver()
    }

    private fun setUpRecyclerView() {
        binding.searchRecView.layoutManager = LinearLayoutManager(context)
        binding.searchRecView.adapter = adapter
    }

    private fun setObserver() {
        viewModel.searchData.nonNullObserve(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }

    private fun setEditText() {
        binding.searchEditText.addTextChangedListener {
            viewModel.sendSearch(it.toString())
        }

    }

    private fun setOnBackClick() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}