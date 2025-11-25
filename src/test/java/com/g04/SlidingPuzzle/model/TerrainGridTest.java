package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.model.enums.FoodType;
import com.g04.SlidingPuzzle.model.terrain.Position;
import com.g04.SlidingPuzzle.model.terrain.TerrainGrid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TerrainGrid class.
 * Tests grid operations including placement, removal, and movement.
 */
public class TerrainGridTest {
    private TerrainGrid grid;

    @BeforeEach
    public void setUp() {
        grid = new TerrainGrid();
    }

    @Test
    public void testGridInitiallyEmpty() {
        for (int row = 0; row < TerrainGrid.GRID_SIZE; row++) {
            for (int col = 0; col < TerrainGrid.GRID_SIZE; col++) {
                Position pos = new Position(row, col);
                assertTrue(grid.isEmpty(pos));
                assertNull(grid.get(pos));
            }
        }
    }

    @Test
    public void testSetAndGetObject() {
        Position pos = new Position(5, 5);
        Food food = new Food(FoodType.KRILL, 3);

        grid.set(pos, food);

        assertFalse(grid.isEmpty(pos));
        assertEquals(food, grid.get(pos));
        assertEquals(pos, food.getPosition());
    }

    @Test
    public void testRemoveObject() {
        Position pos = new Position(3, 7);
        Food food = new Food(FoodType.ANCHOVY, 2);

        grid.set(pos, food);
        assertFalse(grid.isEmpty(pos));

        Food removed = (Food) grid.remove(pos);

        assertEquals(food, removed);
        assertTrue(grid.isEmpty(pos));
        assertNull(grid.get(pos));
    }

    @Test
    public void testMoveObject() {
        Position from = new Position(2, 2);
        Position to = new Position(7, 8);
        Food food = new Food(FoodType.SQUID, 4);

        grid.set(from, food);
        boolean moved = grid.move(from, to);

        assertTrue(moved);
        assertTrue(grid.isEmpty(from));
        assertEquals(food, grid.get(to));
        assertEquals(to, food.getPosition());
    }

    @Test
    public void testIsValidPosition() {
        assertTrue(grid.isValidPosition(new Position(0, 0)));
        assertTrue(grid.isValidPosition(new Position(9, 9)));
        assertTrue(grid.isValidPosition(new Position(5, 5)));

        assertFalse(grid.isValidPosition(new Position(-1, 5)));
        assertFalse(grid.isValidPosition(new Position(5, 10)));
        assertFalse(grid.isValidPosition(new Position(10, 10)));
        assertFalse(grid.isValidPosition(null));
    }

    @Test
    public void testGetEdgePositions() {
        var edgePositions = grid.getEdgePositions();

        // Should have 36 edge positions on a 10x10 grid
        // (10*4 sides - 4 corners counted twice) = 40-4 = 36
        assertEquals(36, edgePositions.size());

        // Verify all edge positions are actually on edges
        for (Position pos : edgePositions) {
            assertTrue(pos.isEdge(TerrainGrid.GRID_SIZE));
        }
    }

    @Test
    public void testGetAllPositions() {
        var allPositions = grid.getAllPositions();

        // Should have 100 positions on a 10x10 grid
        assertEquals(100, allPositions.size());

        // Verify all positions are valid
        for (Position pos : allPositions) {
            assertTrue(grid.isValidPosition(pos));
        }
    }

    @Test
    public void testClearGrid() {
        // Fill some positions
        grid.set(new Position(1, 1), new Food(FoodType.KRILL, 1));
        grid.set(new Position(5, 5), new Food(FoodType.MACKEREL, 5));

        grid.clear();

        // Verify all positions are empty
        for (int row = 0; row < TerrainGrid.GRID_SIZE; row++) {
            for (int col = 0; col < TerrainGrid.GRID_SIZE; col++) {
                assertTrue(grid.isEmpty(new Position(row, col)));
            }
        }
    }
}
