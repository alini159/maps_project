package com.example.maps_project.data;

import static com.example.maps_project.utils.Constants.BASE_URL_API_CEP;
import static com.example.maps_project.utils.Constants.BASE_URL_AWESOME_API;
import static com.example.maps_project.utils.Constants.BASE_URL_VIA_CEP;

import android.content.Context;

import com.example.maps_project.utils.LogManager;
import com.example.maps_project.utils.ViaCepDtoDeserializer;
import com.example.maps_project.data.service.Service;
import com.example.maps_project.utils.SimpleCallback;
import com.example.maps_project.domain.model.ViaCepDto;
import com.example.maps_project.domain.model.ApiCepDto;
import com.example.maps_project.domain.model.AwesomeApiDto;
import com.example.maps_project.domain.Cep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Context context;
    private Service service;

    private String URL;
    private LogManager logManager = new LogManager();

    public Repository(Context context, String url) {
        this.context = context;
        URL = url;
        initialize(url);
    }

    private void initialize(String url) {
        Gson g = new GsonBuilder().registerTypeAdapter(ViaCepDto.class, new ViaCepDtoDeserializer()).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        service = retrofit.create(Service.class);

        final Service service = retrofit.create(Service.class);
    }

    public void getCEP(String CEP, final SimpleCallback<Cep> callback) {
        switch (URL) {
            case BASE_URL_VIA_CEP:
                logManager.logDebug("accessing ->" + BASE_URL_VIA_CEP);
                getFromViaCep(CEP, callback);
                break;
            case BASE_URL_API_CEP:
                logManager.logDebug("accessing ->" + BASE_URL_API_CEP);
                getFromApiCep(CEP, callback);
                break;
            case BASE_URL_AWESOME_API:
                logManager.logDebug("accessing ->" + BASE_URL_AWESOME_API);
                getFromAwesomeApi(CEP, callback);
                break;
        }
    }

    private void getFromViaCep(String CEP, SimpleCallback<Cep> callback) {
        service.getEnderecoByViaCep(CEP).enqueue(new Callback<ViaCepDto>() {
            @Override
            public void onResponse(Call<ViaCepDto> call, Response<ViaCepDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logManager.logDebug("convert ViaCepDto to Cep");
                    Cep cep = new Cep(
                            response.body().getCep(),
                            response.body().getUf(),
                            response.body().getLocalidade(),
                            response.body().getBairro(),
                            response.body().getLogradouro()
                    );
                    callback.onResponse(cep);
                } else {
                    if (response.body() != null) {
                        logManager.logError("access to response fails");
                        callback.onError("erro");
                    } else {
                        logManager.logError("response body is null");
                        callback.onError("erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<ViaCepDto> call, Throwable t) {
                logManager.logError("api access fails");
                t.printStackTrace();
                logManager.logError("StackTrace " + t);
                callback.onError(t.getMessage());
                logManager.logError("error message: " + t.getMessage() + "caused by: " + t.getCause());
            }
        });
    }

    private void getFromApiCep(String CEP, SimpleCallback<Cep> callback) {
        service.getEnderecoByApiCep(CEP).enqueue(new Callback<ApiCepDto>() {
            @Override
            public void onResponse(Call<ApiCepDto> call, Response<ApiCepDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logManager.logDebug("convert ApiCepDto to Cep");
                    Cep cep = new Cep(
                            response.body().getCode(),
                            response.body().getState(),
                            response.body().getCity(),
                            response.body().getDistrict(),
                            response.body().getAddress()
                    );
                    callback.onResponse(cep);
                } else {
                    if (response.body() != null) {
                        logManager.logError("access to response fails");
                        callback.onError("erro");
                    } else {
                        logManager.logError("response body is null");
                        callback.onError("erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiCepDto> call, Throwable t) {
                logManager.logError("api access fails");
                t.printStackTrace();
                logManager.logError("StackTrace " + t);
                callback.onError(t.getMessage());
                logManager.logError("error message: " + t.getMessage() + "caused by: " + t.getCause());
            }
        });
    }

    private void getFromAwesomeApi(String CEP, SimpleCallback<Cep> callback) {
        service.getEnderecoByAwesomeApi(CEP).enqueue(new Callback<AwesomeApiDto>() {
            @Override
            public void onResponse(Call<AwesomeApiDto> call, Response<AwesomeApiDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logManager.logDebug("convert AwesomeApiDto to Cep");
                    Cep cep = new Cep(
                            response.body().getCep(),
                            response.body().getState(),
                            response.body().getCity(),
                            response.body().getDistrict(),
                            response.body().getAddress()
                    );
                    callback.onResponse(cep);
                } else {
                    if (response.body() != null) {
                        logManager.logError("access to response fails");
                        callback.onError("erro");
                    } else {
                        logManager.logError("response body is null");
                        callback.onError("erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<AwesomeApiDto> call, Throwable t) {
                logManager.logError("api access fails");
                t.printStackTrace();
                logManager.logError("StackTrace " + t);
                callback.onError(t.getMessage());
                logManager.logError("error message: " + t.getMessage() + "caused by: " + t.getCause());
            }
        });
    }
}
