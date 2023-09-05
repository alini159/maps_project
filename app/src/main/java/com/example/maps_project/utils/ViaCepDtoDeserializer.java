package com.example.maps_project.utils;


import com.example.maps_project.domain.model.ViaCepDto;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ViaCepDtoDeserializer implements JsonDeserializer {

    @Override
    public ViaCepDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !json.isJsonObject()) {
            throw new JsonParseException("O JSON não é um objeto válido.");
        }

        return new Gson().fromJson(json, ViaCepDto.class);
    }
}

