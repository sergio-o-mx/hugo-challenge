package com.mxrampage.hugochallenge.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.dashboard.adapters.BeersListAdapter
import com.mxrampage.hugochallenge.dashboard.adapters.FavoritesListAdapter
import com.mxrampage.hugochallenge.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var dashboardBinding: FragmentDashboardBinding
    private lateinit var favoritesListAdapter: FavoritesListAdapter
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val beersListAdapter = BeersListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        dashboardBinding.lifecycleOwner = viewLifecycleOwner
        dashboardBinding.dashboardViewModel = dashboardViewModel
        setupViewsPendingProperties()
        setupObservers()
        return dashboardBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        dashboardBinding.progressBar.visibility = View.VISIBLE
        dashboardViewModel.getBeersFromNetwork()
        dashboardViewModel.getFavoritesFromLocalStorage()
    }

    private fun setupViewsPendingProperties() {
        beersListAdapter.onItemClick = { beer ->
            dashboardBinding.root.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(beer.id, false)
            )
        }
        dashboardBinding.recyclerNetworkDisplay.adapter = beersListAdapter
        dashboardBinding.searchBeers.setOnQueryTextListener(object : SimpleQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    dashboardViewModel.searchBeersOnNetwork(query)
                    dashboardBinding.searchBeers.visibility = View.GONE
                }
                return false
            }
        })
        dashboardBinding.searchBeers.setOnCloseListener {
            dashboardBinding.searchBeers.visibility = View.GONE
            false
        }
    }

    private fun setupObservers() {
        dashboardViewModel.beersResponse.observe(viewLifecycleOwner, {
            dashboardBinding.progressBar.visibility = View.GONE
            beersListAdapter.submitList(it)
        })
        dashboardViewModel.favoritesResponse.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                dashboardBinding.progressBar.visibility = View.GONE
                dashboardBinding.linearFavoritesContainer.visibility = View.VISIBLE
                favoritesListAdapter = FavoritesListAdapter()
                favoritesListAdapter.onItemClick = { favorite ->
                    dashboardBinding.root.findNavController().navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(favorite.id, true)
                    )
                }
                dashboardBinding.recyclerLocalDisplay.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                dashboardBinding.recyclerLocalDisplay.adapter = favoritesListAdapter
                favoritesListAdapter.submitList(it)
            }
        })
        dashboardViewModel.errorResponse.observe(viewLifecycleOwner, {
            dashboardBinding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            it.message?.let { error -> Log.e("DashboardFragment", error) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.appBarSearch -> {
                dashboardBinding.searchBeers.isIconified = true
                dashboardBinding.searchBeers.onActionViewExpanded()
                dashboardBinding.searchBeers.visibility = View.VISIBLE
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
