package com.g04.SlidingPuzzle;

import com.g04.SlidingPuzzle.model.IcyTerrain;

/**
 * Main application class for the Sliding Penguins Puzzle Game.
 * As per specification, the main method only initializes an IcyTerrain object.
 */
public class SlidingPuzzleApp {

    public static void main(String[] args) {
        IcyTerrain terrain = new IcyTerrain();
        terrain.startGame();
    }
}
