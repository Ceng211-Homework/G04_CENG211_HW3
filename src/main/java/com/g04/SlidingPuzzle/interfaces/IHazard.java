package com.g04.SlidingPuzzle.interfaces;

import com.g04.SlidingPuzzle.model.enums.HazardType;
import com.g04.SlidingPuzzle.model.terrain.CollisionResult;

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
     * @return A CollisionResult object describing what happened
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


}
