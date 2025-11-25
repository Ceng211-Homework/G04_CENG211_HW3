package com.g04.SlidingPuzzle.exception;

/**
 * Exception thrown when an invalid move is attempted in the game.
 * This includes moves on removed penguins, invalid directions, etc.
 */
public class InvalidMoveException extends RuntimeException {

    /**
     * Creates a new InvalidMoveException with the specified message.
     *
     * @param message The error message
     */
    public InvalidMoveException(String message) {
        super(message);
    }

    /**
     * Creates a new InvalidMoveException with message and cause.
     *
     * @param message The error message
     * @param cause The underlying cause
     */
    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for attempting to move a removed penguin.
     *
     * @param penguinName The name of the removed penguin
     * @return A new InvalidMoveException
     */
    public static InvalidMoveException penguinRemoved(String penguinName) {
        return new InvalidMoveException(
            String.format("Cannot move %s - penguin has been removed from the game", penguinName)
        );
    }

    /**
     * Creates an exception for an invalid direction.
     *
     * @param direction The invalid direction string
     * @return A new InvalidMoveException
     */
    public static InvalidMoveException invalidDirection(String direction) {
        return new InvalidMoveException(
            String.format("Invalid direction: %s. Must be U, D, L, or R", direction)
        );
    }
}
