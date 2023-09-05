package com.example.maps_project.presenter.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.maps_project.data.room.RoomDatabase;
import com.example.maps_project.presenter.viewModel.CepViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final RoomDatabase database;

    public ViewModelFactory(RoomDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CepViewModel.class)) {
            return (T) new CepViewModel(database);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
