package edu.msoe.receiptstorageapp

import android.content.Context
import androidx.room.Room
import edu.msoe.receiptstorageapp.database.ReceiptDatabase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.util.Date
import java.util.UUID

private const val DATABASE_NAME = "receipt-database"

/**
 * ReceiptRepository: Repository class for the Receipt Database
 */
class ReceiptRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val db: ReceiptDatabase = Room
        .databaseBuilder(context.applicationContext,ReceiptDatabase::class.java, DATABASE_NAME)
        .allowMainThreadQueries()
        .build()

    fun getReceiptItems(): Flow<List<Receipt>> {
        return db.receiptDao().getReceiptItems()
    }

    fun getSortedReceiptItems(sortBy: String, direction: Int): Flow<List<Receipt>> {
        return db.receiptDao().getSortedReceiptItems(sortBy, direction)
    }

    fun getReceiptItem(receiptId: UUID): Receipt = db.receiptDao().getReceiptItem(receiptId)

    fun updateReceiptItem(receipt: Receipt) {
        coroutineScope.launch {
            db.receiptDao().updateReceiptItem(receipt)
        }
    }

    suspend fun addReceiptItem(receipt: Receipt) {
        db.receiptDao().addReceiptItem(receipt)
    }

    companion object {
        private var INSTANCE: ReceiptRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ReceiptRepository(context)
            }
        }

        fun get(): ReceiptRepository {
            return INSTANCE?:
            throw IllegalStateException("ReceiptRepository was not initialized.")
        }
    }
}