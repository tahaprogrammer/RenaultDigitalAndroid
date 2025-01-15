package com.maroc.renaultdigitalandroid.services.cars;

import android.util.Log;

import androidx.annotation.NonNull;

import com.maroc.renaultdigitalandroid.entities.CarEntity;
import com.maroc.renaultdigitalandroid.interfaces.retrofit.CarApiService;
import com.maroc.renaultdigitalandroid.providers.RetrofitClientSetup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ListingCarModelOnlineService {

    private static final String TAG = "ListingCarModelOnlineSe";

    public ListingCarModelOnlineService() {
    }

    public void execute() {
        CarApiService carApiService = RetrofitClientSetup.getClient().create(CarApiService.class);

        //Fetch Data
        Call<List<CarEntity>> call = carApiService.getCarModels();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<CarEntity>> call, @NonNull Response<List<CarEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CarEntity> carEntities = response.body();
                    ListingCarModelOnlineService.this.onSuccess((ArrayList<CarEntity>) carEntities);
                } else {
                    System.err.println("Response Error: " + response.code());
                    ListingCarModelOnlineService.this.onFailure(response.code() + "");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CarEntity>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: Error");
                ListingCarModelOnlineService.this.onFailure(t.getMessage());
            }
        });
    }

    public abstract void onSuccess(ArrayList<CarEntity> mCarEntities);

    public abstract void onFailure(String message);

    public abstract void onUnauthorized();
}
