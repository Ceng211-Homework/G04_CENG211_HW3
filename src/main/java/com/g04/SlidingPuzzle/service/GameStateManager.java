package com.g04.SlidingPuzzle.service;

import com.g04.SlidingPuzzle.exception.InvalidGameStateException;
import com.g04.SlidingPuzzle.model.Penguin;

import java.util.List;

/**
 * Service class for managing game state and turn progression.
 * Handles turn counting, penguin ordering, and game-over detection.
 */
public class GameStateManager {
    private int currentTurn;
    private int currentPenguinIndex;
    private final int totalTurns;
    private final List<Penguin> penguins;

    /**
     * Creates a new game state manager.
     *
     * @param penguins The list of penguins in the game
     * @param totalTurns The total number of turns per penguin
     * @throws InvalidGameStateException if penguins list is empty or totalTurns is invalid
     */
    public GameStateManager(List<Penguin> penguins, int totalTurns) {
        if (penguins == null || penguins.isEmpty()) {
            throw new InvalidGameStateException("Cannot start game with no penguins");
        }
        if (totalTurns <= 0) {
            throw new InvalidGameStateException("Total turns must be positive, got: " + totalTurns);
        }
        this.penguins = penguins;
        this.totalTurns = totalTurns;
        this.currentTurn = 1;
        this.currentPenguinIndex = 0;
    }

    /**
     * Gets the current turn number.
     *
     * @return The current turn (1-based)
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Gets the current penguin whose turn it is.
     *
     * @return The current penguin
     * @throws InvalidGameStateException if game is over
     */
    public Penguin getCurrentPenguin() {
        if (isGameOver()) {
            throw new InvalidGameStateException("Cannot get current penguin - game is over");
        }
        return penguins.get(currentPenguinIndex);
    }

    /**
     * Advances to the next penguin's turn.
     * Automatically increments turn number when all penguins have moved.
     *
     * @throws InvalidGameStateException if game is already over
     */
    public void advanceTurn() {
        if (isGameOver()) {
            throw new InvalidGameStateException("Cannot advance turn - game is over");
        }
        currentPenguinIndex++;
        if (currentPenguinIndex >= penguins.size()) {
            currentPenguinIndex = 0;
            currentTurn++;
        }
    }

    /**
     * Checks if the game is over (all turns completed).
     *
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() {
        return currentTurn > totalTurns;
    }

    /**
     * Gets the number of remaining turns.
     *
     * @return Number of turns remaining
     */
    public int getRemainingTurns() {
        return Math.max(0, totalTurns - currentTurn + 1);
    }

    /**
     * Gets the total number of active (non-removed) penguins.
     *
     * @return Count of active penguins
     */
    public int getActivePenguinCount() {
        return (int) penguins.stream().filter(p -> !p.isRemoved()).count();
    }
}
