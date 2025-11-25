package com.g04.SlidingPuzzle.model.penguins;

import com.g04.SlidingPuzzle.model.Penguin;
import com.g04.SlidingPuzzle.model.enums.PenguinType;

/**
 * Represents a Royal Penguin.
 * Special ability: Before sliding, can choose to safely move into an adjacent square
 * (only horizontally and vertically). It's possible to accidentally step out of the
 * grid and fall into water while using this ability.
 */
public class RoyalPenguin extends Penguin {

    /**
     * Creates a new Royal Penguin with the specified name.
     *
     * @param name The penguin's identifier
     */
    public RoyalPenguin(String name) {
        super(name);
    }

    @Override
    public PenguinType getPenguinType() {
        return PenguinType.ROYAL;
    }
}
