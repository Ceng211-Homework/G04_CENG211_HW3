package com.g04.SlidingPuzzle.model.enums;

/**
 * Represents the four types of penguins available in the game.
 * Each penguin type has unique abilities and characteristics.
 */
public enum PenguinType {
    KING("King Penguin", 5),
    EMPEROR("Emperor Penguin", 3),
    ROYAL("Royal Penguin", 0),
    ROCKHOPPER("Rockhopper Penguin", 0);

    private final String displayName;
    private final int specialStopSquare;

    /**
     * Creates a penguin type with its properties.
     *
     * @param displayName The full display name
     * @param specialStopSquare The square number for stop ability (0 if not applicable)
     */
    PenguinType(String displayName, int specialStopSquare) {
        this.displayName = displayName;
        this.specialStopSquare = specialStopSquare;
    }

    /**
     * Gets the display name for this penguin type.
     *
     * @return Full name (e.g., "King Penguin")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the special stop square for this penguin type.
     * Only applicable for King (5) and Emperor (3) penguins.
     *
     * @return The square number, or 0 if not applicable
     */
    public int getSpecialStopSquare() {
        return specialStopSquare;
    }

    /**
     * Gets a random penguin type with equal probability.
     *
     * @return A random PenguinType
     */
    public static PenguinType random() {
        PenguinType[] types = values();
        return types[(int) (Math.random() * types.length)];
    }
}

