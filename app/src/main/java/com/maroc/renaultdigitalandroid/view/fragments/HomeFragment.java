package com.maroc.renaultdigitalandroid.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maroc.renaultdigitalandroid.adapters.cars.ListingCarModelsAdapter;
import com.maroc.renaultdigitalandroid.databinding.FragmentHomeBinding;
import com.maroc.renaultdigitalandroid.dialogs.cars.CarModelDetailDialog;
import com.maroc.renaultdigitalandroid.model.car.CarEntity;
import com.maroc.renaultdigitalandroid.viewmodel.car.CarViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context mContext;
    private Activity mActivity;
    private ListingCarModelsAdapter listingCarModelsAdapter;
    private CarViewModel carViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        mContext = requireContext();
        mActivity = requireActivity();

        this.initViews();
        this.initListeners();
        this.setupViewModel();

        return binding.getRoot();
    }

    private void initViews() {
        listingCarModelsAdapter = new ListingCarModelsAdapter(mContext);
        binding.recyclerViewCarModels.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.recyclerViewCarModels.setLayoutManager(layoutManager);
        binding.recyclerViewCarModels.setAdapter(listingCarModelsAdapter);

        // Handle item clicks
        listingCarModelsAdapter.setOnItemClickingListener(position -> {
            CarEntity carEntity = listingCarModelsAdapter.getCarAtPosition(position);
            CarModelDetailDialog.getInstance(mActivity, mContext, carEntity).show();
        });
    }

    private void initListeners() {
        binding.swipeRefreshLayoutCars.setOnRefreshListener(() -> {
            binding.swipeRefreshLayoutCars.setRefreshing(false);
            startLoading();
            carViewModel.fetchCars();
        });
    }

    private void setupViewModel() {
        carViewModel = new ViewModelProvider(this).get(CarViewModel.class);

        // Observe LiveData for car list updates
        carViewModel.getCarLiveData().observe(getViewLifecycleOwner(), carEntities -> {
            if (carEntities != null && !carEntities.isEmpty()) {
                listingCarModelsAdapter.updateList(carEntities);
                showData();
            } else {
                showNoData();
            }
        });

        // Fetch data initially
        carViewModel.fetchCars();
        startLoading();
    }

    private void startLoading() {
        binding.recyclerViewCarModels.setEnabled(false);
        binding.layoutNoDataFound.getRoot().setVisibility(View.GONE);
        binding.swipeRefreshLayoutCars.setVisibility(View.GONE);
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmer();
    }

    private void showData() {
        binding.recyclerViewCarModels.setEnabled(true);
        binding.swipeRefreshLayoutCars.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.setVisibility(View.GONE);
        binding.shimmerViewContainer.stopShimmer();
        binding.layoutNoDataFound.getRoot().setVisibility(View.GONE);
    }

    private void showNoData() {
        binding.recyclerViewCarModels.setEnabled(false);
        binding.swipeRefreshLayoutCars.setVisibility(View.GONE);
        binding.shimmerViewContainer.setVisibility(View.GONE);
        binding.shimmerViewContainer.stopShimmer();
        binding.layoutNoDataFound.getRoot().setVisibility(View.VISIBLE);
    }
}
