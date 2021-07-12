package com.bartollesina.movieapp.utils


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, {
        it?.let(observer)
    })
}

