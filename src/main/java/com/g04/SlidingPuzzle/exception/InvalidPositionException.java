package com.g04.SlidingPuzzle.exception;

/**
 * Exception thrown when an invalid position is used on the terrain grid.
 * This includes positions outside grid bounds or null positions.
 */
public class InvalidPositionException extends RuntimeException {

    /**
     * Creates a new InvalidPositionException with the specified message.
     *
     * @param message The error message
     */
    public InvalidPositionException(String message) {
        super(message);
    }

    /**
     * Creates a new InvalidPositionException with message and cause.
     *
     * @param message The error message
     * @param cause The underlying cause
     */
    public InvalidPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for a position outside grid bounds.
     *
     * @param row The row coordinate
     * @param col The column coordinate
     * @param gridSize The size of the grid
     * @return A new InvalidPositionException
     */
    public static InvalidPositionException outOfBounds(int row, int col, int gridSize) {
        return new InvalidPositionException(
            String.format("Position (%d, %d) is outside grid bounds (0-%d, 0-%d)",
                row, col, gridSize - 1, gridSize - 1)
        );
    }

    /**
     * Creates an exception for a null position.
     *
     * @return A new InvalidPositionException
     */
    public static InvalidPositionException nullPosition() {
        return new InvalidPositionException("Position cannot be null");
    }
}
