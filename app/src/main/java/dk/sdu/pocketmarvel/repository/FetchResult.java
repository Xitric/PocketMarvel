package dk.sdu.pocketmarvel.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FetchResult<T> {

    @NonNull
    private State state;
    @Nullable
    private T result;
    @Nullable
    private String message;

    public FetchResult(@NonNull State state, @Nullable T result, @Nullable String message) {
        this.state = state;
        this.result = result;
        this.message = message;
    }

    @NonNull
    public State getState() {
        return state;
    }

    @Nullable
    public T getResult() {
        return result;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public enum State {
        Fetching,
        Success,
        Failure,
        Offline
    }
}
