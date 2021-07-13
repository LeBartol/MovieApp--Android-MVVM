package com.bartollesina.movieapp.database

import com.bartollesina.movieapp.models.MovieEntity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}