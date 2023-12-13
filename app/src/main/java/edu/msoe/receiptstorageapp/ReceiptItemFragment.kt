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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.msoe.receiptstorageapp.databinding.FragmentReceiptItemBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date


/**
 * ReceiptItemFragment Class: Provides functionality for the UI fragment responsible for adding or
 * updating a Receipt
 */
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
            // OnClick Listener for 'Save Changes' button
            saveReceiptChangesButton.setOnClickListener {
                // update provider name and transaction total
                receiptDetailViewModel.updateReceipt { oldReceipt ->
                    if (transactionTotalEditText.text.toString().isEmpty()) {
                        oldReceipt.copy(vendorName = receiptProviderEditText.text.toString(), grandTotal = BigDecimal.ZERO)
                    } else {
                        oldReceipt.copy(vendorName = receiptProviderEditText.text.toString(), grandTotal = transactionTotalEditText.text.toString().toBigDecimal())
                    }
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

        // Set Listener for FragmentResult of DatePickerFragment
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            receiptDetailViewModel.updateReceipt { it.copy(date = newDate) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Updates the UI according to changes made to the ReceiptItem
     */
    private fun updateUi(receipt: Receipt) {
        binding.apply {

            transactionDateEditText.text = receipt.date.toString()
            transactionDateEditText.setOnClickListener {
                findNavController().navigate(
                    ReceiptItemFragmentDirections.selectDate(receipt.date)
                )
            }

            if (receiptProviderEditText.text.toString() != receipt.vendorName) {
                receiptProviderEditText.setText(receipt.vendorName)
            }
            if (transactionTotalEditText.text.toString() != "Receipt ID: " + receipt.grandTotal.toString()) {
                transactionTotalEditText.setText(receipt.grandTotal.toString())
            }
            if (receiptIdLabel.text.toString() != receipt.receiptId.toString()) {
                receiptIdLabel.text = "Receipt ID: " + receipt.receiptId.toString()
            }
        }
    }
}