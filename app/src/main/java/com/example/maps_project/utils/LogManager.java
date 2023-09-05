package com.example.maps_project.utils;

import static com.example.maps_project.utils.Constants.DEBUG;
import static com.example.maps_project.utils.Constants.ERROR;

import android.util.Log;

public class LogManager {
    public void logDebug(String message){
        Log.d(DEBUG, message);
    }
    public void logError(String err){
        Log.e(ERROR, err);
    }
}
