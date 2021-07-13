package com.bartollesina.movieapp.favorite_movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartollesina.movieapp.R
import com.bartollesina.movieapp.adapter.MovieRecyclerAdapter
import com.bartollesina.movieapp.databinding.FragmentFavoriteMoviesBinding
import com.bartollesina.movieapp.utils.nonNullObserve
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteMoviesFragment : Fragment() {
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteMoviesVM by viewModel()
    val adapter by lazy { MovieRecyclerAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnSearchClick()
        setUpRecyclerView()
        setObservers()
        viewModel.getAllMovies()
    }

    private fun setOnSearchClick() {
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteMoviesFragment_to_movieSearchFragment)
        }
    }

    private fun setUpRecyclerView() {
        binding.favoriteList.layoutManager = LinearLayoutManager(context)
        binding.favoriteList.adapter = adapter
    }

    private fun setObservers() {
        viewModel.data.nonNullObserve(viewLifecycleOwner, {
            adapter.setData(it)
        })
        viewModel.openDetails.nonNullObserve(viewLifecycleOwner, {
            val action =
                FavoriteMoviesFragmentDirections
                    .actionFavoriteMoviesFragmentToMovieDetailsFragment(it)
            findNavController().navigate(action)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}