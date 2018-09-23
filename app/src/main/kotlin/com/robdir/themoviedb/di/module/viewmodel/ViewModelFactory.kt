package com.robdir.themoviedb.di.module.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = creators[modelClass]
            ?: creators.asIterable().firstOrNull { entry -> modelClass.isAssignableFrom(entry.key) }?.value
            ?: throw IllegalArgumentException("Unknown model class ${modelClass.simpleName}")

        try {
            return creator.get() as T
        } catch (exception: Exception) {
            throw RuntimeException(exception)
        }
    }
}
