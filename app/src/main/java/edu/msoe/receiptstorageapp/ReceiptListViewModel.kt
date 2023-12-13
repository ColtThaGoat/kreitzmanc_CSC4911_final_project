package edu.msoe.receiptstorageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ReceiptListViewModel Class: ViewModel for managing state of the list of all entered Receipt items
 */
class ReceiptListViewModel : ViewModel() {
    private val receiptRepository = ReceiptRepository.get()

    private val _receipts: MutableStateFlow<List<Receipt>> = MutableStateFlow(emptyList())

    private var sortBy: String = ""
    private var direction: Int = 0

    fun setSortBy(sortBy: String) {
        this.sortBy = sortBy
    }

    fun setSortDirection(direction: Int) {
        this.direction = direction
    }

    val receipts: StateFlow<List<Receipt>>
        get() = _receipts.asStateFlow()

    init {
        viewModelScope.launch {

            if (sortBy.isNotEmpty()) {
                receiptRepository.getSortedReceiptItems(sortBy, direction).collect {
                    _receipts.value = it
                }
            } else {
                receiptRepository.getReceiptItems().collect {
                    _receipts.value = it
                }
            }
        }
    }

    suspend fun addReceiptItem(receipt: Receipt) {
        receiptRepository.addReceiptItem(receipt)
    }
}