package edu.msoe.receiptstorageapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.UUID

@Entity
data class ReceiptImage (
    @PrimaryKey
    val imageId: UUID
    // TODO:
    //  image val?
    //  relate to ImageSet entity (foreign key?)
)