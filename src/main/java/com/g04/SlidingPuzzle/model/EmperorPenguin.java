package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.model.enums.PenguinType;

/**
 * Represents an Emperor Penguin.
 * Special ability: Can choose to stop at the third square while sliding.
 * If the direction has less than three free squares, the ability is still considered used.
 */
public class EmperorPenguin extends Penguin {

    /**
     * Creates a new Emperor Penguin with the specified name.
     *
     * @param name The penguin's identifier
     */
    public EmperorPenguin(String name) {
        super(name);
    }

    @Override
    public PenguinType getPenguinType() {
        return PenguinType.EMPEROR;
    }

    /**
     * Gets the square number where this penguin stops when using special ability.
     *
     * @return 3 (stops at third square)
     */
    public int getStopSquare() {
        return getPenguinType().getSpecialStopSquare();
    }
}
