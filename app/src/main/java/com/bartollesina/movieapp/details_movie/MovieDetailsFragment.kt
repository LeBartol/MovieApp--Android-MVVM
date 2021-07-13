package com.bartollesina.movieapp.details_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bartollesina.movieapp.R
import com.bartollesina.movieapp.databinding.FragmentMovieDetailsBinding
import com.bartollesina.movieapp.utils.nonNullObserve
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsVM by viewModel()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(args.imdbId)
        setOnBackClick()
        setOnFavoriteClick()
        viewModel.data.nonNullObserve(viewLifecycleOwner, {
            Glide.with(view)
                .load(it.poster)
                .placeholder(R.drawable.movie_placeholder)
                .into(binding.poster)
            binding.title.text = it.title
            binding.released.text = it.released
            binding.runtime.text = it.runtime
            binding.rating.text = it.imdbRating
            binding.genre.text = it.genre
            binding.plot.text = it.plot
        })
    }

    private fun setOnBackClick() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setOnFavoriteClick() {
        binding.favoriteBtn.setOnClickListener {
            viewModel.sendFavorite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}