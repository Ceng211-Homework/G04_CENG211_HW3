package com.g04.SlidingPuzzle.model.penguins;

import com.g04.SlidingPuzzle.model.Penguin;
import com.g04.SlidingPuzzle.model.enums.Direction;
import com.g04.SlidingPuzzle.model.enums.PenguinType;

/**
 * Represents a Rockhopper Penguin.
 * Special ability: Before sliding, can prepare to jump over one hazard in their path.
 * This ability is not used automatically and can be wasted if there are no hazards.
 * Can only jump to an empty square - if the landing square is not empty, they fail
 * to jump and collide with the hazard.
 */
public class RockhopperPenguin extends Penguin {
    private boolean jumpPrepared;
    private Direction jumpDirection;

    /**
     * Creates a new Rockhopper Penguin with the specified name.
     *
     * @param name The penguin's identifier
     */
    public RockhopperPenguin(String name) {
        super(name);
        this.jumpPrepared = false;
    }

    @Override
    public PenguinType getPenguinType() {
        return PenguinType.ROCKHOPPER;
    }

    /**
     * Prepares to jump over a hazard in the specified direction.
     *
     * @param direction The direction to jump
     */
    public void prepareJump(Direction direction) {
        this.jumpPrepared = true;
        this.jumpDirection = direction;
    }

    /**
     * Checks if jump is prepared.
     *
     * @return true if jump is prepared, false otherwise
     */
    public boolean isJumpPrepared() {
        return jumpPrepared;
    }

    /**
     * Gets the direction for the prepared jump.
     *
     * @return The jump direction, or null if not prepared
     */
    public Direction getJumpDirection() {
        return jumpDirection;
    }

    /**
     * Clears the jump preparation (after jump is executed or failed).
     */
    public void clearJump() {
        this.jumpPrepared = false;
        this.jumpDirection = null;
    }
}
