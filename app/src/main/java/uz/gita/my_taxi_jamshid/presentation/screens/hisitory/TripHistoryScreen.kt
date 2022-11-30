package uz.gita.my_taxi_jamshid.presentation.screens.hisitory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.databinding.ScreenTripHistoryBinding
import uz.gita.my_taxi_jamshid.presentation.presenter.TripHistoryViewModelImpl
import uz.gita.my_taxi_jamshid.utils.extensions.inVisible
import uz.gita.my_taxi_jamshid.utils.extensions.showError
import uz.gita.my_taxi_jamshid.utils.extensions.showMessage
import uz.gita.my_taxi_jamshid.utils.extensions.visible

// Created by Jamshid Isoqov on 11/29/2022

@AndroidEntryPoint
class TripHistoryScreen : Fragment(R.layout.screen_trip_history) {

    private val viewBinding: ScreenTripHistoryBinding by viewBinding()

    private val adapter: TripHistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TripHistoryAdapter()
    }

    private val viewModel: TripHistoryViewModel by viewModels<TripHistoryViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.listTrips.adapter = adapter

        viewModel.loadingFlow.onEach {
            viewBinding.apply {
                if (it) {
                    shimmerLayout.visible()
                    listTrips.inVisible()
                    shimmerLayout.startShimmer()
                } else {
                    shimmerLayout.inVisible()
                    listTrips.visible()
                    shimmerLayout.stopShimmer()
                }
            }
        }
            .launchIn(lifecycleScope)

        viewBinding.imageBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.messageFlow.onEach {
            showMessage(it)
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            showError(it)
        }.launchIn(lifecycleScope)

        viewModel.tripHistoryFlow.onEach {
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.setItemClickListener {
            viewModel.navigateToTripDetails(it)
        }
        viewModel.getAllTrips()
    }

}