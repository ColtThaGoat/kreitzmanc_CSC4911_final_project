package edu.msoe.receiptstorageapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ReceiptItemViewModel(receiptId: UUID): ViewModel() {
    private val receiptRepository = ReceiptRepository.get()

    private val _receipt: MutableStateFlow<Receipt?> = MutableStateFlow(null)
    val receipt: StateFlow<Receipt?> = _receipt.asStateFlow()

    init {
        viewModelScope.launch {
            _receipt.value = receiptRepository.getReceiptItem(receiptId)
        }
    }

    fun updateReceipt(onUpdate: (Receipt) -> Receipt) {
        _receipt.update { oldReceipt ->
            oldReceipt?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        receipt.value?.let { receiptRepository.updateReceiptItem(it) }
    }
}

class ReceiptItemViewModelFactory(
    private val receiptId: UUID
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return ReceiptItemViewModel(receiptId) as T
    }
}