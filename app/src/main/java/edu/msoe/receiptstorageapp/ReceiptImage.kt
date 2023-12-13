package edu.msoe.receiptstorageapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.UUID

@Entity
data class ReceiptImage (
    @PrimaryKey
    val imageId: UUID,
    val imageLocation: String,
    val sequenceNumber: Int,
    val receiptID: UUID
)