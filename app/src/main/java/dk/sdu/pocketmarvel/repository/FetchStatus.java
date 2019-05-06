package dk.sdu.pocketmarvel.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Represents the current status of fetching some data, including a descriptive message. Use this
 * type when the status should not be associated with the resulting data.
 */
public class FetchStatus {

    @NonNull
    private NetworkStatus state;
    @Nullable
    private String message;

    public FetchStatus(@NonNull NetworkStatus state, @Nullable String message) {
        this.state = state;
        this.message = message;
    }

    @NonNull
    public NetworkStatus getState() {
        return state;
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}
