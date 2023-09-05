package com.example.maps_project.data.service;

import com.example.maps_project.domain.model.ViaCepDto;
import com.example.maps_project.domain.model.ApiCepDto;
import com.example.maps_project.domain.model.AwesomeApiDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("{CEP}/json/")
    Call<ViaCepDto> getEnderecoByViaCep(@Path("CEP") String CEP);

    @GET("{CEP}.json/")
    Call<ApiCepDto> getEnderecoByApiCep(@Path("CEP") String CEP);

    @GET("{CEP}")
    Call<AwesomeApiDto> getEnderecoByAwesomeApi(@Path("CEP") String CEP);

}



