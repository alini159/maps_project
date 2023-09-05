package com.example.maps_project.presenter.viewModel;

import static com.example.maps_project.utils.Constants.BASE_URL_API_CEP;
import static com.example.maps_project.utils.Constants.BASE_URL_AWESOME_API;
import static com.example.maps_project.utils.Constants.BASE_URL_VIA_CEP;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.maps_project.data.Repository;
import com.example.maps_project.data.room.Entity;
import com.example.maps_project.data.room.EntityDao;
import com.example.maps_project.data.room.RoomDatabase;
import com.example.maps_project.domain.Cep;
import com.example.maps_project.utils.LogManager;
import com.example.maps_project.utils.SimpleCallback;
import com.example.maps_project.utils.Result;

public class CepViewModel extends androidx.lifecycle.ViewModel {
    private String cep;
    private Context context;
    private EntityDao dao;
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>();
    private LogManager logManager = new LogManager();

    public CepViewModel(RoomDatabase database) {
        dao = database.entityDao();
    }


    public LiveData<Result> getData(Context context, String cep) {
        this.context = context;
        this.cep = cep;
        if (dao.checkDataExist(cep) != 0) {
            resultLiveData.setValue(new Result.Success(creatingCepFromRoom()));
            logManager.logDebug("success to access data on Room");
            return resultLiveData;
        } else {
            logManager.logDebug("this cep Doesn`t exist on database, calling first API");
            viaCepAPI();
        }
        return resultLiveData;
    }

    public void persistData(@NonNull Cep data) {
        if (dao.checkDataExist(data.getCep()) == 0) {
            Entity entity = new Entity();
            entity.setId(data.getCep());
            entity.setData(stringfyData(data));
            dao.insert(entity);
            logManager.logDebug("success to insert data on Room");
        }
    }

    private void viaCepAPI() {
        Repository service = new Repository(context, BASE_URL_VIA_CEP);

        service.getCEP(cep, new SimpleCallback<Cep>() {
            @Override
            public void onResponse(Cep response) {
                logManager.logDebug("success to access first API");
                resultLiveData.setValue(new Result.Success(response));
            }

            @Override
            public void onError(String error) {
                logManager.logError("failure to access first API, trying to access second API");
                apiCep();
            }
        });
    }

    private void apiCep() {
        Repository service = new Repository(context, BASE_URL_API_CEP);

        service.getCEP(cep, new SimpleCallback<Cep>() {

            @Override
            public void onResponse(Cep response) {
                logManager.logDebug("success to access second API");
                resultLiveData.setValue(new Result.Success(response));
            }

            @Override
            public void onError(String error) {
                logManager.logError("failure to access second API, trying to access third API");
                awesomeApi();
            }
        });
    }

    private void awesomeApi() {
        Repository service = new Repository(context, BASE_URL_AWESOME_API);

        service.getCEP(cep, new SimpleCallback<Cep>() {

            @Override
            public void onResponse(Cep response) {
                logManager.logDebug("success to access third API");
                resultLiveData.setValue(new Result.Success(response));
            }

            @Override
            public void onError(String error) {
                logManager.logError("failure to access third API, Sending Error Message");
                resultLiveData.setValue(new Result.Error(error));
            }
        });
    }

    private Cep creatingCepFromRoom() {
        String[] list = dao.findData(cep).getData().split(";");
        Cep cepFromRoom = new Cep(list[0], list[1], list[2], list[3], list[4]);
        return cepFromRoom;
    }

    private String stringfyData(Cep data) {
        return data.getCep() + ";" + data.getEstado() + ";" + data.getCidade() + ";" + data.getBairro() + ";" + data.getEndereco();
    }
}
