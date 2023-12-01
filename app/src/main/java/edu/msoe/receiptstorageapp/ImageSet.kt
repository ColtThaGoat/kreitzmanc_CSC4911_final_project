package edu.msoe.receiptstorageapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.UUID

@Entity
data class ImageSet (
    @PrimaryKey
    val imageSetId: UUID
    // TODO: relate to receiptItem (foreign key?)
)

