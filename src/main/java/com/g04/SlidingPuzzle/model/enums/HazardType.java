package com.g04.SlidingPuzzle.model.enums;

/**
 * Represents the four types of hazards available in the game.
 * Each hazard type has specific behavior and display symbol.
 */
public enum HazardType {
    LIGHT_ICE_BLOCK("LB", "Light Ice Block", true),
    HEAVY_ICE_BLOCK("HB", "Heavy Ice Block", false),
    SEA_LION("SL", "Sea Lion", true),
    HOLE_IN_ICE("HI", "Hole in Ice", false);

    private final String displaySymbol;
    private final String displayName;
    private final boolean canSlide;

    /**
     * Creates a hazard type with its properties.
     *
     * @param displaySymbol The two-letter symbol for grid display
     * @param displayName Full name for display
     * @param canSlide Whether this hazard type can slide on ice
     */
    HazardType(String displaySymbol, String displayName, boolean canSlide) {
        this.displaySymbol = displaySymbol;
        this.displayName = displayName;
        this.canSlide = canSlide;
    }

    /**
     * Gets the display symbol for this hazard type.
     *
     * @return The display symbol (e.g., "LB", "HB")
     */
    public String getDisplaySymbol() {
        return displaySymbol;
    }

    /**
     * Gets the display name for this hazard type.
     *
     * @return Full name (e.g., "Light Ice Block")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if this hazard type can slide on ice.
     *
     * @return true if hazard slides, false otherwise
     */
    public boolean canSlide() {
        return canSlide;
    }

    /**
     * Gets a random hazard type with equal probability.
     *
     * @return A random HazardType
     */
    public static HazardType random() {
        HazardType[] types = values();
        return types[(int) (Math.random() * types.length)];
    }
}
