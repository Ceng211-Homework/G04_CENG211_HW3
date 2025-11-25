package com.g04.SlidingPuzzle.model.hazards;

import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.Hazard;
import com.g04.SlidingPuzzle.model.Penguin;
import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Represents a Light Ice Block hazard.
 * This ice block starts moving in the transmitted direction after a penguin or
 * another sliding hazard collides into it. Moving penguins or other sliding
 * hazards
 * stop on the square they were at the moment of collision. A sliding
 * LightIceBlock
 * can fall from the edges. The colliding penguin is temporarily stunned and
 * their
 * next turn is automatically skipped.
 */
public class LightIceBlock extends Hazard {

    /**
     * Creates a new Light Ice Block.
     */
    public LightIceBlock() {
        super(HazardType.LIGHT_ICE_BLOCK);
    }

    @Override
    public CollisionResult handleCollision(ITerrainObject collidingObject) {
        if (collidingObject instanceof Penguin) {
            ((Penguin) collidingObject).setStunned(true);
            return CollisionResult.slideHazard("Penguin stunned, ice block starts sliding");
        }
        return CollisionResult.slideHazard("Object hits ice block, block slides");
    }
}
