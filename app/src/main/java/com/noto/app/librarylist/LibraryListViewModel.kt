package com.noto.app.librarylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noto.app.domain.repository.LibraryRepository
import com.noto.app.domain.source.LocalStorage
import com.noto.app.util.LayoutManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val LayoutManagerKey = "Library_List_Layout_Manager"

class LibraryListViewModel(private val libraryRepository: LibraryRepository, private val storage: LocalStorage) : ViewModel() {

    val libraries = libraryRepository.getLibraries()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val layoutManager = storage.get(LayoutManagerKey)
        .map { LayoutManager.valueOf(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, LayoutManager.Grid)

    fun countNotes(libraryId: Long): Int = runBlocking {
        libraryRepository.countLibraryNotes(libraryId)
    }

    fun updateLayoutManager(value: LayoutManager) = viewModelScope.launch {
        storage.put(LayoutManagerKey, value.toString())
    }
}