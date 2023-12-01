package edu.msoe.receiptstorageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ReceiptListViewModel"
class ReceiptListViewModel : ViewModel() {
    private val receiptRepository = ReceiptRepository.get()

    private val _receipts: MutableStateFlow<List<Receipt>> = MutableStateFlow(emptyList())

    val receipts: StateFlow<List<Receipt>>
        get() = _receipts.asStateFlow()

    init {
        viewModelScope.launch {
            receiptRepository.getReceiptItems().collect {
                _receipts.value = it
            }
        }
    }

    suspend fun addReceiptItem(receipt: Receipt) {
        receiptRepository.addReceiptItem(receipt)
    }
}