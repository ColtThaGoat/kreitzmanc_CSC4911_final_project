package edu.msoe.receiptstorageapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Entity
data class Receipt (
    @PrimaryKey val receiptId: UUID,
    val date: Date,
    val vendorName: String,
    val grandTotal: BigDecimal
)