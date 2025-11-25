package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.exception.InvalidPositionException;
import com.g04.SlidingPuzzle.model.enums.Direction;

/**
 * Represents a position on the terrain grid.
 * Immutable value object for row and column coordinates.
 * Note: Validation is optional since positions can be temporarily out of bounds during pathfinding.
 */
public class Position {
    private final int row;
    private final int col;

    /**
     * Creates a new position with the specified row and column.
     * Note: Does not validate bounds to allow flexible pathfinding.
     * Use isWithinBounds() to check validity for a specific grid size.
     *
     * @param row The row coordinate (0-based)
     * @param col The column coordinate (0-based)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets the row coordinate.
     *
     * @return The row coordinate
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column coordinate.
     *
     * @return The column coordinate
     */
    public int getCol() {
        return col;
    }

    /**
     * Creates a new position by moving in the specified direction.
     *
     * @param direction The direction to move
     * @return A new Position object representing the moved location
     */
    public Position move(Direction direction) {
        return new Position(row + direction.getRowDelta(), col + direction.getColDelta());
    }

    /**
     * Checks if this position is within the bounds of a grid.
     *
     * @param gridSize The size of the grid (assumes square grid)
     * @return true if position is within bounds, false otherwise
     */
    public boolean isWithinBounds(int gridSize) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    /**
     * Checks if this position is on the edge of the grid.
     *
     * @param gridSize The size of the grid
     * @return true if position is on any edge, false otherwise
     */
    public boolean isEdge(int gridSize) {
        return row == 0 || row == gridSize - 1 || col == 0 || col == gridSize - 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
