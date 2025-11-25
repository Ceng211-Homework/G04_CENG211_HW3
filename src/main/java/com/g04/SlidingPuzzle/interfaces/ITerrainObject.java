package com.g04.SlidingPuzzle.interfaces;

import com.g04.SlidingPuzzle.model.terrain.Position;

/**
 * Interface for all objects that can exist on the terrain grid.
 * This includes penguins, hazards, and food items.
 */
public interface ITerrainObject {

    /**
     * Gets the current position of this object on the grid.
     *
     * @return The position of this object
     */
    Position getPosition();

    /**
     * Sets the position of this object on the grid.
     *
     * @param position The new position
     */
    void setPosition(Position position);

    /**
     * Gets the display symbol for this object in the grid rendering.
     * This should match the legend in the specification (e.g., "P1", "Kr", "HB").
     *
     * @return The display symbol as a string
     */
    String getDisplaySymbol();

    /**
     * Checks if this object can slide on ice.
     *
     * @return true if this object slides, false otherwise
     */
    boolean canSlide();
}
