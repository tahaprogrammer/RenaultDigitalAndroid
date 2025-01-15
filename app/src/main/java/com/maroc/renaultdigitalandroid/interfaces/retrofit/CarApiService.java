package com.maroc.renaultdigitalandroid.interfaces.retrofit;

import com.maroc.renaultdigitalandroid.entities.CarEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CarApiService {

    @GET("renaultJobCapgemini/carModels.json")
    Call<List<CarEntity>> getCarModels();
}
