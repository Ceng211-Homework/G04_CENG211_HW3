package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.HazardType;

/**
 * Represents a Heavy Ice Block hazard.
 * This ice block cannot be moved. Anything that collides with it stops in its
 * tracks.
 * The colliding penguin loses the lightest food item they are carrying as a
 * penalty.
 * If the penguin is not carrying any food item, it is unaffected.
 */
public class HeavyIceBlock extends Hazard {

    /**
     * Creates a new Heavy Ice Block.
     */
    public HeavyIceBlock() {
        super(HazardType.HEAVY_ICE_BLOCK);
    }

    @Override
    public CollisionResult handleCollision(ITerrainObject collidingObject) {
        if (collidingObject instanceof Penguin) {
            Penguin penguin = (Penguin) collidingObject;
            Food lostFood = penguin.removeLightestFood();
            String message = lostFood != null
                    ? "Penguin loses food, stops"
                    : "Penguin stops (no food to lose)";
            return CollisionResult.stop(message);
        }
        return CollisionResult.stop("Object stopped by heavy ice block");
    }
}
