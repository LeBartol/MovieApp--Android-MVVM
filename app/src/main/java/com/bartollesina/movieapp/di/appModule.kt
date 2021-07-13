package com.bartollesina.movieapp.di

import android.content.Context
import androidx.room.Room
import com.bartollesina.movieapp.database.MovieDao
import com.bartollesina.movieapp.database.MovieDataBase
import com.bartollesina.movieapp.details_movie.MovieDetailsVM
import com.bartollesina.movieapp.favorite_movies.FavoriteMoviesVM
import com.bartollesina.movieapp.networking.MovieRestInterface
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.search_movie.MovieSearchVM
import com.bartollesina.movieapp.utils.ResourceProvider
import kotlinx.coroutines.FlowPreview
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@FlowPreview
val appModule = module {

    single { provideDatabase(androidContext()) }
    single { provideMovieDao(get()) }
    factory { provideMovieRestInterface(get()) }
    single { provideRetrofit() }
    factory { ResourceProvider(androidContext()) }
    single { MovieRepository(get(),get()) }
    viewModel { MovieSearchVM(get()) }
    viewModel { MovieDetailsVM(get()) }
    viewModel{FavoriteMoviesVM(get(),get())}
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
        .client(OkHttpClient().newBuilder().build())
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideMovieRestInterface(retrofit: Retrofit): MovieRestInterface =
    retrofit.create(MovieRestInterface::class.java)

fun provideDatabase(context: Context): MovieDataBase {
    return Room.databaseBuilder(
        context,
        MovieDataBase::class.java,
        "movie_table"
    ).build()
}

fun provideMovieDao(dataBase: MovieDataBase): MovieDao {
    return dataBase.movieDao()
}