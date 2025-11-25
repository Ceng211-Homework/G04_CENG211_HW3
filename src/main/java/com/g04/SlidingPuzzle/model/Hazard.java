package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.exception.InvalidGameStateException;
import com.g04.SlidingPuzzle.interfaces.IHazard;
import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Abstract base class for all hazard types.
 * Hazards are obstacles on the terrain that can affect penguins and other objects.
 */
public abstract class Hazard implements IHazard {
    private final HazardType hazardType;
    private Position position;

    /**
     * Creates a new hazard of the specified type.
     *
     * @param hazardType The type of hazard
     * @throws InvalidGameStateException if hazardType is null
     */
    protected Hazard(HazardType hazardType) {
        if (hazardType == null) {
            throw InvalidGameStateException.nullParameter("hazardType");
        }
        this.hazardType = hazardType;
    }

    @Override
    public HazardType getHazardType() {
        return hazardType;
    }

    @Override
    public String getDisplaySymbol() {
        return hazardType.getDisplaySymbol();
    }

    @Override
    public boolean canSlide() {
        return hazardType.canSlide();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Default collision handling - subclasses should override for specific behavior.
     */
    @Override
    public CollisionResult handleCollision(ITerrainObject collidingObject) {
        return CollisionResult.stop("Collided with " + hazardType.getDisplayName());
    }
}
