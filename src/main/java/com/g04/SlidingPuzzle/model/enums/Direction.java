package com.g04.SlidingPuzzle.model.enums;

/**
 * Represents the four cardinal directions for movement on the terrain grid.
 */
public enum Direction {
    UP(-1, 0, "U", "UPWARDS"),
    DOWN(1, 0, "D", "DOWNWARDS"),
    LEFT(0, -1, "L", "LEFT"),
    RIGHT(0, 1, "R", "RIGHT");

    private final int rowDelta;
    private final int colDelta;
    private final String shortCode;
    private final String displayName;

    /**
     * Creates a direction with movement deltas and display information.
     *
     * @param rowDelta The change in row (-1 for up, +1 for down)
     * @param colDelta The change in column (-1 for left, +1 for right)
     * @param shortCode Single character code (U, D, L, R)
     * @param displayName Full name for display
     */
    Direction(int rowDelta, int colDelta, String shortCode, String displayName) {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
        this.shortCode = shortCode;
        this.displayName = displayName;
    }

    /**
     * Gets the row delta for this direction.
     *
     * @return The row delta (-1, 0, or 1)
     */
    public int getRowDelta() {
        return rowDelta;
    }

    /**
     * Gets the column delta for this direction.
     *
     * @return The column delta (-1, 0, or 1)
     */
    public int getColDelta() {
        return colDelta;
    }

    /**
     * Gets the short code for this direction.
     *
     * @return Single character code (U, D, L, R)
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Gets the display name for this direction.
     *
     * @return Full name (UPWARDS, DOWNWARDS, LEFT, RIGHT)
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the opposite direction.
     *
     * @return The opposite direction (UP<->DOWN, LEFT<->RIGHT)
     */
    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> throw new IllegalStateException("Unknown direction");
        };
    }

    /**
     * Parses a direction from user input (case-insensitive).
     *
     * @param input The user input (U, D, L, R or full names)
     * @return The corresponding Direction object
     * @throws IllegalArgumentException if input is not one of the stated options
     */
    public static Direction fromInput(String input) throws IllegalArgumentException {
        if (input == null)
            throw new IllegalArgumentException("input cannot be null");

        String normalized = input.trim().toUpperCase();

        for (Direction dir : values()) {
            if (dir.shortCode.equals(normalized) || dir.toString().equals(normalized)) {
                return dir;
            }
        }
        throw new IllegalArgumentException("Unknown direction: " + input);
    }
}
