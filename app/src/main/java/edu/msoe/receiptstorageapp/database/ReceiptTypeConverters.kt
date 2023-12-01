package edu.msoe.receiptstorageapp.database

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.Date

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
    fun bigDecimalToDouble(transactionTotal: BigDecimal): Double {
        return transactionTotal.toDouble()
    }

    @TypeConverter
    fun stringToBigDecimal(transactionTotal: Double?): BigDecimal {
        if (transactionTotal == null) return BigDecimal.ZERO
        return BigDecimal.valueOf(transactionTotal) ?: BigDecimal.ZERO
    }
}