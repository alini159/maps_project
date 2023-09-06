package com.example.maps_project.data.service;

import com.example.maps_project.domain.model.ViaCepDto;
import com.example.maps_project.domain.model.ApiCepDto;
import com.example.maps_project.domain.model.AwesomeApiDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("{CEP}/json/")
    Call<ViaCepDto> getAddressByViaCep(@Path("CEP") String CEP);

    @GET("{CEP}.json/")
    Call<ApiCepDto> getAddressByApiCep(@Path("CEP") String CEP);

    @GET("{CEP}")
    Call<AwesomeApiDto> getAddressByAwesomeApi(@Path("CEP") String CEP);

}



