package dk.sdu.pocketmarvel.repository;

import org.jetbrains.annotations.NotNull;

public class DataFetchError {

    private String message;

    public DataFetchError(String message) {
        this.message = message;
    }

    @NotNull
    @Override
    public String toString() {
        return "Error: " + message;
    }
}
