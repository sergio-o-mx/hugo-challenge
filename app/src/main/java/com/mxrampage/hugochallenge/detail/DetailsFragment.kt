package com.mxrampage.hugochallenge.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mxrampage.hugochallenge.R
import com.mxrampage.hugochallenge.dashboard.Beers
import com.mxrampage.hugochallenge.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var detailsBinding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    private var beerId: Long = 0
    private var isFavorite: Boolean = false
    private lateinit var beer: Beers

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        detailsBinding.lifecycleOwner = viewLifecycleOwner
        detailsBinding.detailsViewModel = detailsViewModel
        beerId = DetailsFragmentArgs.fromBundle(requireArguments()).id
        isFavorite = DetailsFragmentArgs.fromBundle(requireArguments()).isFavorite
        setupPendingViews()
        setupUIStateObserver()
        return detailsBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        detailsBinding.progressBar2.visibility = View.VISIBLE
        detailsViewModel.getBeerDetailsFromNetwork(beerId)
    }

    private fun setupPendingViews() {
        detailsBinding.textBeerDetails.movementMethod = ScrollingMovementMethod()
    }

    private fun setupUIStateObserver() {
        detailsViewModel.beerDetails.observe(viewLifecycleOwner, {
            detailsBinding.progressBar2.visibility = View.GONE
            Glide.with(detailsBinding.imageBeerDetailsContainer)
                .load(it[0].imageURL)
                .into(detailsBinding.imageBeerDetailsContainer)
            detailsBinding.textBeerDetails.text = detailsViewModel.createInformationList(it[0])
            beer = it[0]
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.detailsFavorite)
        if (isFavorite) menuItem.setIcon(R.mipmap.ic_like_enabled)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.detailsFavorite -> {
                isFavorite = if (isFavorite) {
                    detailsViewModel.removeBeerFromFavorites(beer)
                    item.setIcon(R.mipmap.ic_like_enabled)
                    false
                } else {
                    detailsViewModel.markBeerAsFavorite(beer)
                    item.setIcon(R.mipmap.ic_like_disabled)
                    true
                }
                requireActivity().invalidateOptionsMenu()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
