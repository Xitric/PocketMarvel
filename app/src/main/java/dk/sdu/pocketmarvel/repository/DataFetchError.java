package dk.sdu.pocketmarvel.repository;

import android.support.annotation.NonNull;

public class DataFetchError {

    private String message;

    public DataFetchError(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "Error: " + message;
    }
}
