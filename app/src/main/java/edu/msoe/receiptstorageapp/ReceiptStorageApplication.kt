package edu.msoe.receiptstorageapp

import android.app.Application

class ReceiptStorageApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ReceiptRepository.initialize(this)
    }
}