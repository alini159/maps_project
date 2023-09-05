package com.example.maps_project.utils;

public interface SimpleCallback<T> {
    void onResponse(T response);

    void onError(String error);
}
