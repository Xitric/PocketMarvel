package dk.sdu.pocketmarvel.repository;

/**
 * Represents the current status of a networked request.
 */
public enum NetworkStatus {
    Fetching,
    Success,
    Failure,
    Offline
}
