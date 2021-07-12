package com.bartollesina.movieapp.di

import com.bartollesina.movieapp.networking.MovieRestInterface
import com.bartollesina.movieapp.repository.MovieRepository
import com.bartollesina.movieapp.search.MovieSearchVM
import okhttp3.OkHttpClient

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {


    factory { provideMovieRestInterface(get()) }
    single { provideRetrofit() }
    single { MovieRepository(get()) }
    viewModel { MovieSearchVM(get()) }

}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl("http://www.omdbapi.com/")
        .client(OkHttpClient().newBuilder().build())
        .addConverterFactory(GsonConverterFactory.create()).build()
}


fun provideMovieRestInterface(retrofit: Retrofit): MovieRestInterface =
    retrofit.create(MovieRestInterface::class.java)
