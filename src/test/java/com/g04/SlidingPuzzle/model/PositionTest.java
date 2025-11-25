package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.model.enums.Direction;
import com.g04.SlidingPuzzle.model.terrain.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Position class.
 * Tests immutable value object behavior, movement, and boundary checking.
 */
public class PositionTest {

    @Test
    public void testPositionCreation() {
        Position pos = new Position(5, 7);
        assertEquals(5, pos.getRow());
        assertEquals(7, pos.getCol());
    }

    @Test
    public void testPositionMove() {
        Position pos = new Position(5, 5);

        Position up = pos.move(Direction.UP);
        assertEquals(4, up.getRow());
        assertEquals(5, up.getCol());

        Position down = pos.move(Direction.DOWN);
        assertEquals(6, down.getRow());
        assertEquals(5, down.getCol());

        Position left = pos.move(Direction.LEFT);
        assertEquals(5, left.getRow());
        assertEquals(4, left.getCol());

        Position right = pos.move(Direction.RIGHT);
        assertEquals(5, right.getRow());
        assertEquals(6, right.getCol());
    }

    @Test
    public void testIsWithinBounds() {
        Position valid = new Position(5, 5);
        assertTrue(valid.isWithinBounds(10));

        Position edgeTopLeft = new Position(0, 0);
        assertTrue(edgeTopLeft.isWithinBounds(10));

        Position edgeBottomRight = new Position(9, 9);
        assertTrue(edgeBottomRight.isWithinBounds(10));

        Position outOfBoundsNegative = new Position(-1, 5);
        assertFalse(outOfBoundsNegative.isWithinBounds(10));

        Position outOfBoundsTooLarge = new Position(5, 10);
        assertFalse(outOfBoundsTooLarge.isWithinBounds(10));
    }

    @Test
    public void testIsEdge() {
        Position edge1 = new Position(0, 5);
        assertTrue(edge1.isEdge(10));

        Position edge2 = new Position(9, 5);
        assertTrue(edge2.isEdge(10));

        Position edge3 = new Position(5, 0);
        assertTrue(edge3.isEdge(10));

        Position edge4 = new Position(5, 9);
        assertTrue(edge4.isEdge(10));

        Position center = new Position(5, 5);
        assertFalse(center.isEdge(10));
    }

    @Test
    public void testPositionEquality() {
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(3, 4);
        Position pos3 = new Position(3, 5);

        assertEquals(pos1, pos2);
        assertNotEquals(pos1, pos3);
        assertEquals(pos1.hashCode(), pos2.hashCode());
    }

    @Test
    public void testPositionImmutability() {
        Position original = new Position(5, 5);
        Position moved = original.move(Direction.UP);

        // Original should not be modified
        assertEquals(5, original.getRow());
        assertEquals(5, original.getCol());

        // Moved should be new instance
        assertEquals(4, moved.getRow());
        assertNotSame(original, moved);
    }
}
