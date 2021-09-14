package com.noto.app.data.database

import android.content.Context
import androidx.room.*
import com.noto.app.data.source.LibraryDao
import com.noto.app.data.source.NoteDao
import com.noto.app.domain.model.Label
import com.noto.app.domain.model.Library
import com.noto.app.domain.model.Note
import com.noto.app.domain.model.NoteLabel

private const val NOTO_DATABASE = "Noto Database"

@TypeConverters(
    NotoColorConverter::class,
    InstantConverter::class,
    LayoutManagerConvertor::class,
    SortingConverter::class,
    SortingOrderConverter::class,
)
@Database(
    entities = [Note::class, Library::class, Label::class, NoteLabel::class],
    version = 13,
    autoMigrations = [
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11),
        AutoMigration(from = 11, to = 12),
        AutoMigration(from = 12, to = 13),
//        AutoMigration(from = 13, to = 14, spec = Migrations.DeleteSortingTypeColumn::class),
//        AutoMigration(from = 14, to = 15, spec = Migrations.DeleteSortingMethodColumn::class),
//        AutoMigration(from = 15, to = 16, spec = Migrations.RenameIsStarredColumn::class),
//        AutoMigration(from = 16, to = 17, spec = Migrations.DeleteLabelAndNoteLabelTables::class),
//        AutoMigration(from = 17, to = 18, spec = Migrations.DeleteIsSetNewNoteCursorOnTitleColumn::class),
    ],
)
abstract class NotoDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    abstract val libraryDao: LibraryDao

    companion object {

        @Volatile
        private var INSTANCE: NotoDatabase? = null

        fun getInstance(context: Context): NotoDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context)
                .also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NotoDatabase::class.java, NOTO_DATABASE)
                .addMigrations(
//                    RemoveNotoPrefix,
//                    RenameNotosTableToNotes,
//                    RemoveLibraryPrefix,
//                    AddSortingColumns,
//                    RemoveNotoIcon,
                )
                .build()
    }
}