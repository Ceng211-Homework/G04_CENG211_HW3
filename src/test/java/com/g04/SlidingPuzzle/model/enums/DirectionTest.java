package com.g04.SlidingPuzzle.model.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Direction enum.
 * Tests direction properties, parsing, and opposite direction calculation.
 */
public class DirectionTest {

    @Test
    public void testDirectionDeltas() {
        assertEquals(-1, Direction.UP.getRowDelta());
        assertEquals(0, Direction.UP.getColDelta());

        assertEquals(1, Direction.DOWN.getRowDelta());
        assertEquals(0, Direction.DOWN.getColDelta());

        assertEquals(0, Direction.LEFT.getRowDelta());
        assertEquals(-1, Direction.LEFT.getColDelta());

        assertEquals(0, Direction.RIGHT.getRowDelta());
        assertEquals(1, Direction.RIGHT.getColDelta());
    }

    @Test
    public void testDirectionOpposites() {
        assertEquals(Direction.DOWN, Direction.UP.getOpposite());
        assertEquals(Direction.UP, Direction.DOWN.getOpposite());
        assertEquals(Direction.RIGHT, Direction.LEFT.getOpposite());
        assertEquals(Direction.LEFT, Direction.RIGHT.getOpposite());
    }

    @Test
    public void testDirectionFromInputUpperCase() {
        assertEquals(Direction.UP, Direction.fromInput("U"));
        assertEquals(Direction.DOWN, Direction.fromInput("D"));
        assertEquals(Direction.LEFT, Direction.fromInput("L"));
        assertEquals(Direction.RIGHT, Direction.fromInput("R"));
    }

    @Test
    public void testDirectionFromInputLowerCase() {
        assertEquals(Direction.UP, Direction.fromInput("u"));
        assertEquals(Direction.DOWN, Direction.fromInput("d"));
        assertEquals(Direction.LEFT, Direction.fromInput("l"));
        assertEquals(Direction.RIGHT, Direction.fromInput("r"));
    }

    @Test
    public void testDirectionFromInputWithWhitespace() {
        assertEquals(Direction.UP, Direction.fromInput("  U  "));
        assertEquals(Direction.DOWN, Direction.fromInput(" d "));
    }

    @Test
    public void testDirectionFromInputInvalid() {
        assertNull(Direction.fromInput("X"));
        assertNull(Direction.fromInput(""));
        assertNull(Direction.fromInput(null));
    }

    @Test
    public void testDirectionDisplayNames() {
        assertEquals("UPWARDS", Direction.UP.getDisplayName());
        assertEquals("DOWNWARDS", Direction.DOWN.getDisplayName());
        assertEquals("LEFT", Direction.LEFT.getDisplayName());
        assertEquals("RIGHT", Direction.RIGHT.getDisplayName());
    }
}
