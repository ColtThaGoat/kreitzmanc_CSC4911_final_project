package edu.msoe.receiptstorageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.msoe.receiptstorageapp.databinding.ReceiptListItemBinding
import java.math.BigDecimal
import java.util.UUID

private const val TAG = "ReceiptListAdapter"

class ReceiptListAdapter (
    private val receipts: List<Receipt>,
    private val onReceiptClicked: (receiptId: UUID) -> Unit
) : RecyclerView.Adapter<ReceiptHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceiptHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ReceiptListItemBinding.inflate(inflater, parent, false)
        return ReceiptHolder(binding)
    }

    override fun getItemCount() = receipts.size

    override fun onBindViewHolder(holder: ReceiptHolder, position: Int) {
        val receipt = receipts[position]
        holder.bind(receipt, onReceiptClicked)
    }
}

class ReceiptHolder(
    val binding: ReceiptListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(receipt: Receipt, onReceiptClicked: (receiptId: UUID) -> Unit) {
        binding.dateTextview.text = receipt.date.toString()
        binding.vendorTextview.text = receipt.vendorName
        binding.grandTotalTextview.text = ("$") + receipt.grandTotal.toString()
//        binding.grandTotalTextview.text = String.format("%d", receipt.grandTotal)

        binding.root.setOnClickListener {
            onReceiptClicked(receipt.receiptId)
        }
    }
}