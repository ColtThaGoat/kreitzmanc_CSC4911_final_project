package edu.msoe.receiptstorageapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.msoe.receiptstorageapp.Receipt
import edu.msoe.receiptstorageapp.ReceiptImage

@Database(entities = [ Receipt::class, ReceiptImage::class ], version=1)
@TypeConverters(ReceiptTypeConverters::class)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
}