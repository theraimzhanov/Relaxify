package com.raimzhanov.sweatapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raimzhanov.sweatapp.data.local.AppDatabase
import com.raimzhanov.sweatapp.data.local.OrderRepository
import com.raimzhanov.sweatapp.databinding.FragmentOrdersBinding
import com.raimzhanov.sweatapp.ui.adapters.OrderHistoryAdapter
import com.raimzhanov.sweatapp.ui.viewModel.OrderVMFactory
import com.raimzhanov.sweatapp.ui.viewModel.OrderViewModel

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var adapter: OrderHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dao = AppDatabase.getDatabase(requireContext()).orderDao()
        val repo = OrderRepository(dao)
        orderViewModel =
            ViewModelProvider(this, OrderVMFactory(repo))[OrderViewModel::class.java]

        adapter = OrderHistoryAdapter()

        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = adapter

        orderViewModel.getOrders { orders ->
            if (orders.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvOrders.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvOrders.visibility = View.VISIBLE
                adapter.submitList(orders)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}