package com.g04.SlidingPuzzle.exception;

/**
 * Exception thrown when the game is in an invalid state.
 * This includes uninitialized terrain, invalid parameters, etc.
 */
public class InvalidGameStateException extends RuntimeException {

    /**
     * Creates a new InvalidGameStateException with the specified message.
     *
     * @param message The error message
     */
    public InvalidGameStateException(String message) {
        super(message);
    }

    /**
     * Creates a new InvalidGameStateException with message and cause.
     *
     * @param message The error message
     * @param cause The underlying cause
     */
    public InvalidGameStateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception for invalid food weight.
     *
     * @param weight The invalid weight
     * @return A new InvalidGameStateException
     */
    public static InvalidGameStateException invalidFoodWeight(int weight) {
        return new InvalidGameStateException(
            String.format("Food weight must be between 1-5, got: %d", weight)
        );
    }

    /**
     * Creates an exception for null required parameter.
     *
     * @param paramName The parameter name
     * @return A new InvalidGameStateException
     */
    public static InvalidGameStateException nullParameter(String paramName) {
        return new InvalidGameStateException(
            String.format("Parameter '%s' cannot be null", paramName)
        );
    }
}
