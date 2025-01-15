package com.maroc.renaultdigitalandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.maroc.renaultdigitalandroid.adapters.cars.ListingCarModelsAdapter;
import com.maroc.renaultdigitalandroid.databinding.FragmentHomeBinding;
import com.maroc.renaultdigitalandroid.entities.CarEntity;
import com.maroc.renaultdigitalandroid.services.cars.ListingCarModelOnlineService;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private Context mContext;
    private Activity mActivity;
    private ListingCarModelsAdapter listingCarModelsAdapter;
    private final ArrayList<CarEntity> carEntities = new ArrayList<>();
    private boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        mContext = getContext();
        mActivity = getActivity();

        this.initViews();
        this.initListeners();
        this.listingJobsOnlineService(true);

        return binding.getRoot();
    }

    private void initViews() {
        listingCarModelsAdapter = new ListingCarModelsAdapter(mContext);
        binding.recyclerViewJobs.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.recyclerViewJobs.setLayoutManager(layoutManager);
        binding.recyclerViewJobs.setAdapter(listingCarModelsAdapter);
        listingCarModelsAdapter.setOnItemClickingListener(position -> {
            //CarEntity carEntity = carEntities.get(position);
            //JobDetailDialog.getInstance(mActivity, mContext, carEntity).show();
            Toast.makeText(mContext, "Not Yet", Toast.LENGTH_SHORT).show();
        });
    }

    private void initListeners() {
        binding.swipeRefreshLayoutJobs.setOnRefreshListener(() -> {
            binding.swipeRefreshLayoutJobs.setRefreshing(false);
            carEntities.clear();
            this.listingJobsOnlineService(true);
        });
    }

    public void listingJobsOnlineService(boolean loadingAnimation) {
        if (loadingAnimation) {
            this.startLoading();
        }
        new ListingCarModelOnlineService() {
            @Override
            public void onSuccess(ArrayList<CarEntity> mCarEntities) {
                carEntities.addAll(mCarEntities);
                listingCarModelsAdapter.updateList(carEntities);
                if (loadingAnimation) {
                    stopLoading();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(String message) {
                if (loadingAnimation) {
                    stopLoading();
                }
                isLoading = false;
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnauthorized() {
                if (loadingAnimation) {
                    stopLoading();
                }
                isLoading = false;
                Toast.makeText(mContext, "onUnauthorized", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void startLoading() {
        carEntities.clear();
        binding.layoutNoDataFound.getRoot().setVisibility(View.GONE);
        binding.swipeRefreshLayoutJobs.setVisibility(View.GONE);
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.startShimmer();
    }

    private void stopLoading() {
        binding.swipeRefreshLayoutJobs.setVisibility(View.VISIBLE);
        binding.shimmerViewContainer.setVisibility(View.GONE);
        binding.shimmerViewContainer.stopShimmer();
        if (carEntities.isEmpty()) {
            binding.swipeRefreshLayoutJobs.setVisibility(View.GONE);
            binding.layoutNoDataFound.getRoot().setVisibility(View.VISIBLE);
        }
    }
}
