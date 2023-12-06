package edu.msoe.receiptstorageapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.msoe.receiptstorageapp.databinding.FragmentReceiptRecyclerBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.Date
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReceiptListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceiptListFragment : Fragment() {
    private var _binding: FragmentReceiptRecyclerBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Inaccessible binding. Ensure view is visible."
        }

    private val receiptListViewModel: ReceiptListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReceiptRecyclerBinding.inflate(inflater, container, false)
        binding.receiptRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                receiptListViewModel.receipts.collect { receipts ->
                    binding.receiptRecyclerView.adapter =
                        ReceiptListAdapter(receipts) { receiptId ->
                            findNavController().navigate(
                                ReceiptListFragmentDirections.showReceipt(receiptId))} }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_receipt_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_receipt_item -> {
                showNewReceiptItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewReceiptItem() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newReceipt = Receipt(
                receiptId = UUID.randomUUID(),
                date = Date(),
                vendorName = "",
                grandTotal = "0.0".toBigDecimal()
            )
            receiptListViewModel.addReceiptItem(newReceipt)
            findNavController().navigate(
                ReceiptListFragmentDirections.showReceipt(newReceipt.receiptId)
            )
        }
    }
}

