package com.noto.app.domain.repository

import com.noto.app.domain.model.FolderIdWithNotesCount
import com.noto.app.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getAllMainNotes(): Flow<List<Note>>

    fun getNotesByLibraryId(libraryId: Long): Flow<List<Note>>

    fun getArchivedNotesByLibraryId(libraryId: Long): Flow<List<Note>>

    fun getNoteById(noteId: Long): Flow<Note>

    fun getLibrariesNotesCount(): Flow<List<FolderIdWithNotesCount>>

    suspend fun createNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun clearNotes()
}