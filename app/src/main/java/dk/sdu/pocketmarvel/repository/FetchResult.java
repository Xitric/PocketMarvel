package dk.sdu.pocketmarvel.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Represents the current status of fetching some data, including a descriptive message and the best
 * representation of the result which is currently available. Depending on the
 * {@link NetworkStatus}, this representation might only be temporary.
 */
public class FetchResult<T> extends FetchStatus {

    @Nullable
    private T result;

    public FetchResult(@NonNull NetworkStatus state, @Nullable T result, @Nullable String message) {
        super(state, message);
        this.result = result;
    }

    @Nullable
    public T getResult() {
        return result;
    }
}
