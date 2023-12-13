package edu.msoe.receiptstorageapp

import androidx.lifecycle.ViewModel

class ReceiptSearchViewModel : ViewModel(){

    private var sortBy: String = ""
    private var direction: Int = 0

    fun setSortBy(sortBy: String) {
        this.sortBy = sortBy
    }

    fun setSortDirection(direction: Int) {
        this.direction = direction
    }

}