package com.noto.app.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "labels",
//    foreignKeys = [ForeignKey(
//        entity = Library::class,
//        parentColumns = ["id"],
//        childColumns = ["library_id"],
//        onDelete = ForeignKey.CASCADE
//    )]
)
data class Label(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "label_id")
    val id: Long = 0,

//    @ColumnInfo(name = "library_id")
//    val libraryId: Long,

    @ColumnInfo(name = "label_title")
    val title: String,

    @ColumnInfo(name = "label_noto_color")
    val color: NotoColor
)