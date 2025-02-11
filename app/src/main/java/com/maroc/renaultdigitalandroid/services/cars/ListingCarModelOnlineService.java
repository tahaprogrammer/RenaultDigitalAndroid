package com.maroc.renaultdigitalandroid.services.cars;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.maroc.renaultdigitalandroid.model.car.CarEntity;
import com.maroc.renaultdigitalandroid.interfaces.retrofit.CarApiService;
import com.maroc.renaultdigitalandroid.providers.RetrofitClientSetup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingCarModelOnlineService {

    private static final String TAG = "ListingCarModelOnlineSe";

    public ListingCarModelOnlineService() {
    }

    public LiveData<List<CarEntity>> execute() {
        CarApiService carApiService = RetrofitClientSetup.getClient().create(CarApiService.class);
        MutableLiveData<List<CarEntity>> carLiveData = new MutableLiveData<>();

        //Fetch Data
        Call<List<CarEntity>> call = carApiService.getCarModels();
        if(call == null) {
            Log.e(TAG, "error: call is null");
            carLiveData.setValue(null);
            return carLiveData;
        }
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<CarEntity>> call, @NonNull Response<List<CarEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    carLiveData.setValue(response.body());
                } else {
                    System.err.println("Response Error: " + response.code());
                    carLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarEntity>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: Error");
                carLiveData.setValue(null);
            }
        });
        return carLiveData;
    }
}
