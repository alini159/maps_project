package com.example.maps_project.utils;

import com.example.maps_project.domain.Cep;

public abstract class Result {
    private Result() {
    }

    public static final class Success extends Result {
        private final Cep data;

        public Success(Cep data) {
            this.data = data;
        }

        public Cep getData() {
            return data;
        }
    }

    public static final class Error extends Result {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
