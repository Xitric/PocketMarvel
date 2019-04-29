package dk.sdu.pocketmarvel.repository;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Interface describing a data entity that can be stored locally but must occasionally be re-fetched
 * from a remote data source.
 */
public interface Expireable {

    @NonNull
    Date getExpiration();

    void setExpiration(@NonNull Date expiration);
}
