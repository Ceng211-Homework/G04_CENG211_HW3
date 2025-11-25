package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Represents a Sea Lion hazard.
 * A penguin that hits a SeaLion bounces from the SeaLion and starts sliding in
 * the opposite direction. Meanwhile, the penguin's movements are transmitted to
 * the SeaLion, so the SeaLion starts sliding in the initial direction of the
 * penguin.
 * After collision, both animals can fall from the edges and collide into
 * anything
 * else in the IcyTerrain. If a LightIceBlock collides with a SeaLion, the
 * LightIceBlock's movement is transmitted to SeaLion and LightIceBlock stops
 * moving.
 */
public class SeaLion extends Hazard {

    /**
     * Creates a new Sea Lion.
     */
    public SeaLion() {
        super(HazardType.SEA_LION);
    }

    @Override
    public CollisionResult handleCollision(ITerrainObject collidingObject) {
        if (collidingObject instanceof Penguin) {
            return CollisionResult.bounce("Penguin bounces back, sea lion slides forward");
        } else if (collidingObject instanceof LightIceBlock) {
            return CollisionResult.slideHazard("Ice block stops, sea lion slides");
        }
        return CollisionResult.slideHazard("Sea lion starts sliding");
    }
}
