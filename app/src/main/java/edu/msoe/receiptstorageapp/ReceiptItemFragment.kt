package edu.msoe.receiptstorageapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import edu.msoe.receiptstorageapp.databinding.FragmentReceiptItemBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal

private const val TAG = "ReceiptItemFragment"
class ReceiptItemFragment : Fragment() {

    private var _binding: FragmentReceiptItemBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Binding Inaccessible. Ensure view is visible"
    }
    private val args: ReceiptItemFragmentArgs by navArgs()

    private val receiptDetailViewModel: ReceiptItemViewModel by viewModels {
        ReceiptItemViewModelFactory(args.receiptId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentReceiptItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            receiptProviderEditText.doOnTextChanged { text, _, _, _ ->
                receiptDetailViewModel.updateReceipt { oldReceipt ->
                    oldReceipt.copy(vendorName = text.toString())
                }
            }

            transactionTotalEditText.doOnTextChanged { text, _, _, _ ->
                receiptDetailViewModel.updateReceipt { oldReceipt ->
                    oldReceipt.copy(grandTotal = text.toString().toDouble())
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                receiptDetailViewModel.receipt.collect { receipt ->
                    receipt?.let { updateUi(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(receipt: Receipt) {
        binding.apply {
            if (receiptProviderEditText.text.toString() != receipt.vendorName) {
                receiptProviderEditText.setText(receipt.vendorName)
            }
            if (transactionTotalEditText.text.toString() != receipt.grandTotal.toString()) {
                transactionTotalEditText.setText(receipt.grandTotal.toString())
            }

            // TODO: onclicklistener for save receipt button?
        }
    }
}