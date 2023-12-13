package edu.msoe.receiptstorageapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.receiptstorageapp.ImageSet
import edu.msoe.receiptstorageapp.ReceiptImage
import edu.msoe.receiptstorageapp.Receipt
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

@Dao
interface ReceiptDao {
    // ------------ ReceiptItem Database Operations ------------
    @Insert
    suspend fun addReceiptItem(receipt: Receipt)

    @Query("SELECT * FROM receipt WHERE receiptId=(:receiptId)")
    fun getReceiptItem(receiptId: UUID): Receipt

    @Query("SELECT * FROM receipt")
    fun getReceiptItems(): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'date' AND :direction = 0 THEN date END DESC, " +
            "CASE WHEN :sortBy = 'date' AND :direction = 1 THEN date END ASC, " +
            "CASE WHEN :sortBy = 'provider' AND :direction = 0 THEN vendorName END DESC, " +
            "CASE WHEN :sortBy = 'provider' AND :direction = 1 THEN vendorName END ASC, " +
            "CASE WHEN :sortBy = 'grandTotal' AND :direction = 0 THEN grandTotal END DESC, " +
            "CASE WHEN :sortBy = 'grandTotal' AND :direction = 1 THEN grandTotal END ASC")
    fun getSortedReceiptItems(sortBy: String, direction: Int): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt WHERE date=(:date)")
    fun getReceiptItems(date: Date): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt WHERE vendorName=(:vendorName)")
    fun getReceiptItems(vendorName: String): Flow<List<Receipt>>

    @Query("SELECT * FROM receipt WHERE grandTotal=(:grandTotal)")
    fun getReceiptItems(grandTotal: BigDecimal): List<Receipt>

    @Update
    suspend fun updateReceiptItem(receipt: Receipt)

    @Query("UPDATE receipt SET date=(:date) WHERE receiptId=(:receiptId)")
    fun updateReceiptItem(receiptId: UUID, date: Date)

    @Query("UPDATE receipt SET vendorName=(:vendorName) WHERE receiptId=(:receiptId)")
    fun updateReceiptItem(vendorName: String, receiptId: UUID)

    @Query("UPDATE receipt SET grandTotal=(:grandTotal) WHERE receiptId=(:receiptId)")
    fun updateReceiptItem(grandTotal: BigDecimal, receiptId: UUID)

    @Query("DELETE FROM receipt")
    fun deleteReceiptItems()

//    @Query("DELETE FROM receipt WHERE receiptId(:receiptId)")
//    fun deleteReceiptItem(receiptId: UUID)

    // ------------ ImageSet Database Operations ------------
//    @Insert
//    fun addReceiptImageSet(imageSet: ImageSet)
//
//    @Query("DELETE FROM imageSet")
//    fun deleteReceiptImageSets()
//
//    @Query("DELETE FROM imageSet WHERE imageSetId(:imageSetId)")
//    fun deleteReceiptImageSet(imageSetId: UUID)

    // ------------ ReceiptImage Database Operations ------------
    @Insert
    fun addReceiptImage(receiptImage: ReceiptImage)

//    @Query("DELETE FROM receiptImage")
//    fun deleteReceiptImages()
//
//    @Query("DELETE FROM receiptImage WHERE receiptImageId(:receiptImageId)")
//    fun deleteReceiptImage(receiptImageId: UUID)


}