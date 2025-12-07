package com.g04.SlidingPuzzle.model.hazards;

import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.Hazard;
import com.g04.SlidingPuzzle.model.Penguin;
import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Represents a Hole in Ice hazard.
 * Anything that slides into a HoleInIce falls into it.
 * Naturally, HoleInIce cannot move or slide.
 * If a penguin falls into a HoleInIce, that penguin is removed from the game.
 * However, any food items collected are still counted at the end of the game.
 * If a LightIceBlock or a SeaLion slides into a HoleInIce, those sliding
 * objects fall to the hole and plug it.
 * When a HoleInIce is plugged, any sliding objects can pass through it
 * without any issues.
 */
public class HoleInIce extends Hazard {
    private boolean isPlugged;

    /**
     * Creates a new HoleInIce hazard.
     */
    public HoleInIce() {
        super(HazardType.HOLE_IN_ICE);
        this.isPlugged = false;
    }

    /**
     * Checks if this hole is plugged.
     *
     * @return true if plugged, false otherwise
     */
    public boolean isPlugged() {
        return isPlugged;
    }

    /**
     * Plugs this hole (when a LightIceBlock or SeaLion falls into it).
     */
    public void plug() {
        this.isPlugged = true;
    }

    @Override
    public String getDisplaySymbol() {
        return isPlugged ? "PH" : getHazardType().getDisplaySymbol();
    }

    @Override
    public boolean hasSpecialState() {
        return true; // Hole can be plugged
    }

    @Override
    public CollisionResult handleCollision(ITerrainObject collidingObject) {
        if (isPlugged) {
            return CollisionResult.stop("Object passes over plugged hole");
        }

        if (collidingObject instanceof Penguin) {
            ((Penguin) collidingObject).remove();
            return CollisionResult.remove("Penguin falls into hole");
        } else if (collidingObject instanceof LightIceBlock || collidingObject instanceof SeaLion) {
            plug();
            return CollisionResult.remove("Hazard falls in and plugs hole");
        }

        return CollisionResult.remove("Object falls into hole");
    }
}
