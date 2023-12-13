package edu.msoe.receiptstorageapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import edu.msoe.receiptstorageapp.databinding.FragmentSearchReceiptsBinding

class ReceiptSearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchReceiptsBinding

//    private val args: ReceiptSearchFragmentArgs by navArgs()

//    private val receiptSearchViewModel: ReceiptSearchViewModel = args.viewModel

//    private val receiptSearchViewModel: ReceiptListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchReceiptsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            var direction: Int
            var checkedRadioId: String

            toggleSortDirectionButton.setOnCheckedChangeListener { _, isChecked ->
                direction = if (isChecked)
                    1
                else
                    0

//                receiptSearchViewModel.setSortDirection(direction)
            }

            sortRadioGroup.setOnCheckedChangeListener { _, _ ->
                checkedRadioId = sortRadioGroup.checkedRadioButtonId.toString();

//                when (checkedRadioId) {
//                    "sortByDateRadioButton" -> receiptSearchViewModel.setSortBy("date")
//                    "sortByProviderRadioButton" -> receiptSearchViewModel.setSortBy("vendorName")
//                    "sortByGrandTotalRadioButton" -> receiptSearchViewModel.setSortBy("grandTotal")
//                    else -> {
//                        receiptSearchViewModel.setSortBy("")
//                    }
//                }
            }
        }
    }


}