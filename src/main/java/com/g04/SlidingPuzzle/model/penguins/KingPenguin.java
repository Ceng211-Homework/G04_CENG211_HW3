package com.g04.SlidingPuzzle.model.penguins;

import com.g04.SlidingPuzzle.model.Penguin;
import com.g04.SlidingPuzzle.model.enums.PenguinType;

/**
 * Represents a King Penguin.
 * Special ability: Can choose to stop at the fifth square while sliding.
 * If the direction has less than five free squares, the ability is still considered used.
 */
public class KingPenguin extends Penguin {

    /**
     * Creates a new King Penguin with the specified name.
     *
     * @param name The penguin's identifier
     */
    public KingPenguin(String name) {
        super(name);
    }

    @Override
    public PenguinType getPenguinType() {
        return PenguinType.KING;
    }

    /**
     * Gets the square number where this penguin stops when using special ability.
     *
     * @return 5 (stops at fifth square)
     */
    public int getStopSquare() {
        return getPenguinType().getSpecialStopSquare();
    }
}
