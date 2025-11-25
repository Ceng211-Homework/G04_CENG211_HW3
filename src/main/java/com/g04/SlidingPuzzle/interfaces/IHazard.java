package com.g04.SlidingPuzzle.interfaces;

import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Interface for all hazard objects on the terrain.
 * Hazards are obstacles that affect penguins and other sliding objects.
 * Extends ITerrainObject to be placeable on the grid.
 */
public interface IHazard extends ITerrainObject {

    /**
     * Gets the type of this hazard.
     *
     * @return The hazard type enum
     */
    HazardType getHazardType();

    /**
     * Handles collision when another object hits this hazard.
     * This method defines the hazard's collision behavior.
     *
     * @param collidingObject The object that collided with this hazard
     * @return A collision result describing what happened
     */
    CollisionResult handleCollision(ITerrainObject collidingObject);

    /**
     * Checks if this hazard has special state.
     * For example, HoleInIce can be plugged.
     *
     * @return true if hazard has special state, false otherwise
     */
    default boolean hasSpecialState() {
        return false;
    }

    /**
     * Simple result object for collision handling.
     * Contains information about what happened during collision.
     */
    class CollisionResult {
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
}
