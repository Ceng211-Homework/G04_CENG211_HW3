package com.g04.SlidingPuzzle.model.terrain;

/**
 * Simple result object for collision handling.
 * Contains information about what happened during collision.
 */
public class CollisionResult {
    public final boolean objectStopped;
    public final boolean objectBounced;
    public final boolean hazardStartsSliding;
    public final boolean objectRemoved;
    public final String message;

    public CollisionResult(boolean objectStopped, boolean objectBounced,
                           boolean hazardStartsSliding, boolean objectRemoved, String message) {
        this.objectStopped = objectStopped;
        this.objectBounced = objectBounced;
        this.hazardStartsSliding = hazardStartsSliding;
        this.objectRemoved = objectRemoved;
        this.message = message;
    }

    public static CollisionResult stop(String message) {
        return new CollisionResult(true, false, false, false, message);
    }

    public static CollisionResult bounce(String message) {
        return new CollisionResult(true, true, false, false, message);
    }

    public static CollisionResult slideHazard(String message) {
        return new CollisionResult(true, false, true, false, message);
    }

    public static CollisionResult remove(String message) {
        return new CollisionResult(false, false, false, true, message);
    }
}
