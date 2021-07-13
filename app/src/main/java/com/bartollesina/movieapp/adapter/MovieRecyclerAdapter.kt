package com.bartollesina.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bartollesina.movieapp.R
import com.bartollesina.movieapp.databinding.HeaderCardBinding
import com.bartollesina.movieapp.databinding.MovieCardBinding
import com.bartollesina.movieapp.search_movie.LayoutType
import com.bartollesina.movieapp.search_movie.MovieSingleUi
import com.bumptech.glide.Glide


class MovieRecyclerAdapter(private val onItemClickedListener: OnItemClickedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val movieListUi = mutableListOf<MovieSingleUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LayoutType.Header.ordinal -> {
                val binding =
                    HeaderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieHeaderViewHolder(binding)
            }
            LayoutType.Movie.ordinal -> {
                val binding =
                    MovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieCardViewHolder(binding, onItemClickedListener)
            }
            else -> {
                throw RuntimeException("No MovieRecyclerAdapter viewType: $viewType")
            }
        }
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is MovieHeaderViewHolder -> {
                viewHolder.bind(movieListUi[position])
            }
            is MovieCardViewHolder -> {
                viewHolder.bind(movieListUi[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return movieListUi[position].type.ordinal
    }

    override fun getItemCount() = movieListUi.size

    internal class MovieHeaderViewHolder(
        private val binding: HeaderCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieSingleUi: MovieSingleUi) {
            binding.tvHeader.text = movieSingleUi.year.toString()
        }
    }

    internal class MovieCardViewHolder(
        private val binding: MovieCardBinding,
        private val onItemClickedListener: OnItemClickedListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieSingleUi: MovieSingleUi) {
            binding.tvTitle.text = movieSingleUi.title
            Glide.with(this.itemView.context)
                .load(movieSingleUi.posterUrl)
                .placeholder(R.drawable.movie_placeholder)
                .into(binding.moviePoster)
            binding.root.setOnClickListener {
                onItemClickedListener.onItemClick(movieSingleUi.id)
            }
        }
    }

    internal class EmptyViewHolder(
        private val binding: View,
    ) : RecyclerView.ViewHolder(binding)

    fun setData(list: List<MovieSingleUi>) {
        this.movieListUi.clear()
        this.movieListUi.addAll(list)
        notifyDataSetChanged()
    }

}