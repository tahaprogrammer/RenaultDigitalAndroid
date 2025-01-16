package com.maroc.renaultdigitalandroid.viewmodel.car;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.maroc.renaultdigitalandroid.interfaces.retrofit.CarApiService;
import com.maroc.renaultdigitalandroid.model.car.CarEntity;
import com.maroc.renaultdigitalandroid.providers.RetrofitClientSetup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarViewModel extends ViewModel {

    private static final String TAG = "CarViewModel";
    private final CarApiService carApiService;
    private final MutableLiveData<List<CarEntity>> carLiveData = new MutableLiveData<>();

    public CarViewModel() {
        carApiService = RetrofitClientSetup.getClient().create(CarApiService.class);
    }

    public MutableLiveData<List<CarEntity>> getCarLiveData() {
        return carLiveData;
    }

    public void fetchCars() {
        //Fetch Data
        Call<List<CarEntity>> call = carApiService.getCarModels();
        if (call == null) {
            Log.e(TAG, "error: call is null");
            carLiveData.setValue(null);
            return;
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
    }
}
