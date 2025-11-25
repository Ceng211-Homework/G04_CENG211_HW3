package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.interfaces.ITerrainObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the 10x10 grid of terrain squares.
 * This is a pure data structure for storing and accessing terrain objects.
 * Uses ArrayList of ArrayLists to fulfill homework requirements.
 */
public class TerrainGrid {
    public static final int GRID_SIZE = 10;
    private final List<List<ITerrainObject>> grid;

    /**
     * Creates a new empty terrain grid of size 10x10.
     * All squares are initialized to null (empty).
     */
    public TerrainGrid() {
        grid = new ArrayList<>(GRID_SIZE);
        for (int row = 0; row < GRID_SIZE; row++) {
            List<ITerrainObject> rowList = new ArrayList<>(GRID_SIZE);
            for (int col = 0; col < GRID_SIZE; col++) {
                rowList.add(null);
            }
            grid.add(rowList);
        }
    }

    /**
     * Gets the object at the specified position.
     *
     * @param position The position to check
     * @return The object at that position, or null if empty
     */
    public ITerrainObject get(Position position) {
        if (!isValidPosition(position)) {
            return null;
        }
        return grid.get(position.getRow()).get(position.getCol());
    }

    /**
     * Sets an object at the specified position.
     *
     * @param position The position to set
     * @param object The object to place (null for empty)
     */
    public void set(Position position, ITerrainObject object) {
        if (!isValidPosition(position)) {
            return;
        }
        grid.get(position.getRow()).set(position.getCol(), object);
        if (object != null) {
            object.setPosition(position);
        }
    }

    /**
     * Checks if a position is empty (no object present).
     *
     * @param position The position to check
     * @return true if empty, false if occupied or invalid
     */
    public boolean isEmpty(Position position) {
        return isValidPosition(position) && get(position) == null;
    }

    /**
     * Checks if a position is within the grid bounds.
     *
     * @param position The position to check
     * @return true if valid, false otherwise
     */
    public boolean isValidPosition(Position position) {
        return position != null && position.isWithinBounds(GRID_SIZE);
    }

    /**
     * Removes an object from the grid at the specified position.
     *
     * @param position The position to clear
     * @return The removed object, or null if position was empty
     */
    public ITerrainObject remove(Position position) {
        ITerrainObject obj = get(position);
        set(position, null);
        return obj;
    }

    /**
     * Moves an object from one position to another.
     * The source position is cleared and the target position is set.
     *
     * @param from The source position
     * @param to The target position
     * @return true if move succeeded, false if invalid positions
     */
    public boolean move(Position from, Position to) {
        if (!isValidPosition(from) || !isValidPosition(to)) {
            return false;
        }
        ITerrainObject obj = remove(from);
        if (obj != null) {
            set(to, obj);
            return true;
        }
        return false;
    }

    /**
     * Gets all positions on the grid edges (for penguin placement).
     *
     * @return List of edge positions
     */
    public List<Position> getEdgePositions() {
        List<Position> edges = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Position pos = new Position(row, col);
                if (pos.isEdge(GRID_SIZE)) {
                    edges.add(pos);
                }
            }
        }
        return edges;
    }

    /**
     * Gets all positions on the grid (for random placement).
     *
     * @return List of all positions
     */
    public List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }

    /**
     * Clears the entire grid (sets all positions to null).
     */
    public void clear() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                grid.get(row).set(col, null);
            }
        }
    }
}
