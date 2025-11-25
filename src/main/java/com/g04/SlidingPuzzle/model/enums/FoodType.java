package com.g04.SlidingPuzzle.model.enums;

/**
 * Represents the five types of food items available in the game.
 * Each food type has a specific display symbol for the grid.
 */
public enum FoodType {
    KRILL("Kr"),
    CRUSTACEAN("Cr"),
    ANCHOVY("An"),
    SQUID("Sq"),
    MACKEREL("Ma");

    private final String displaySymbol;

    /**
     * Creates a food type with its display symbol.
     *
     * @param displaySymbol The two-letter symbol for grid display
     */
    FoodType(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    /**
     * Gets the display symbol for this food type.
     *
     * @return The display symbol (e.g., "Kr", "Cr")
     */
    public String getDisplaySymbol() {
        return displaySymbol;
    }

    /**
     * Gets a random food type with equal probability.
     *
     * @return A random FoodType
     */
    public static FoodType random() {
        FoodType[] types = values();
        return types[(int) (Math.random() * types.length)];
    }
}
