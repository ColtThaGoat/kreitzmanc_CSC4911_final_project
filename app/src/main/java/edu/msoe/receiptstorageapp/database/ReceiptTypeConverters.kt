package edu.msoe.receiptstorageapp.database

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.Date


/**
 * ReceiptTypeConverters Class: Defines all TypeConverters needed for communication between database
 * and UI
 */
class ReceiptTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(msSinceEpoch: Long): Date {
        return Date(msSinceEpoch)
    }

    @TypeConverter
    fun fromBigDecimal(transactionTotal: BigDecimal): String {
        return transactionTotal.toString()
    }

    @TypeConverter
    fun toBigDecimal(transactionTotal: String): BigDecimal {
        if( transactionTotal.isEmpty() ) {
            return BigDecimal.ZERO
        }
        return transactionTotal.toBigDecimal()
    }
}