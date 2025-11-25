package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.service.CollisionHandler;
import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.Direction;
import com.g04.SlidingPuzzle.model.enums.HazardType;
import com.g04.SlidingPuzzle.model.enums.PenguinType;
import com.g04.SlidingPuzzle.model.hazards.HeavyIceBlock;
import com.g04.SlidingPuzzle.model.hazards.HoleInIce;
import com.g04.SlidingPuzzle.model.hazards.LightIceBlock;
import com.g04.SlidingPuzzle.model.hazards.SeaLion;
import com.g04.SlidingPuzzle.model.penguins.EmperorPenguin;
import com.g04.SlidingPuzzle.model.penguins.KingPenguin;
import com.g04.SlidingPuzzle.model.penguins.RockhopperPenguin;
import com.g04.SlidingPuzzle.model.penguins.RoyalPenguin;
import com.g04.SlidingPuzzle.model.terrain.Position;
import com.g04.SlidingPuzzle.model.terrain.TerrainGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the icy terrain game board.
 * Manages the terrain grid, penguin/hazard/food placement, and grid rendering.
 */
public class IcyTerrain {
    private final TerrainGrid grid;
    private final List<Penguin> penguins;
    private Penguin playerPenguin;
    private CollisionHandler collisionHandler;
    private Scanner scanner;

    /**
     * Creates a new icy terrain with an empty grid.
     */
    public IcyTerrain() {
        this.grid = new TerrainGrid();
        this.penguins = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initializes the terrain with penguins, hazards, and food items.
     * - 3 penguins placed on edge squares
     * - 15 hazards placed randomly
     * - 20 food items placed randomly
     * One penguin is randomly assigned as the player's penguin.
     */
    public void initialize() {
        // Place 3 penguins on edges
        placePenguins();

        // Place 15 hazards
        placeHazards();

        // Place 20 food items
        placeFood();

        // Randomly assign player penguin
        playerPenguin = penguins.get((int) (Math.random() * penguins.size()));

        // Initialize collision handler with grid
        this.collisionHandler = new CollisionHandler(grid);
    }

    /**
     * Places 3 penguins on random edge positions.
     * Each penguin has a random type (King, Emperor, Royal, or Rockhopper).
     */
    private void placePenguins() {
        List<Position> edgePositions = grid.getEdgePositions();
        Collections.shuffle(edgePositions);

        for (int i = 0; i < 3; i++) {
            String name = "P" + (i + 1);
            Penguin penguin = createRandomPenguin(name);
            Position pos = edgePositions.get(i);
            grid.set(pos, penguin);
            penguins.add(penguin);
        }
    }

    /**
     * Creates a random penguin of a random type.
     *
     * @param name The penguin's identifier
     * @return A new penguin of random type
     */
    private Penguin createRandomPenguin(String name) {
        PenguinType type = PenguinType.random();
        switch (type) {
            case KING:
                return new KingPenguin(name);
            case EMPEROR:
                return new EmperorPenguin(name);
            case ROYAL:
                return new RoyalPenguin(name);
            case ROCKHOPPER:
                return new RockhopperPenguin(name);
            default:
                throw new IllegalStateException("Unknown penguin type");
        }
    }

    /**
     * Places 15 hazards randomly on the grid.
     * Hazards cannot occupy the same space as penguins.
     */
    private void placeHazards() {
        List<Position> availablePositions = getEmptyPositions();
        Collections.shuffle(availablePositions);

        for (int i = 0; i < 15 && i < availablePositions.size(); i++) {
            Hazard hazard = createRandomHazard();
            grid.set(availablePositions.get(i), hazard);
        }
    }

    /**
     * Creates a random hazard of a random type using HazardType enum.
     *
     * @return A new hazard of random type
     */
    private Hazard createRandomHazard() {
        HazardType type = HazardType.random();
        switch (type) {
            case LIGHT_ICE_BLOCK:
                return new LightIceBlock();
            case HEAVY_ICE_BLOCK:
                return new HeavyIceBlock();
            case SEA_LION:
                return new SeaLion();
            case HOLE_IN_ICE:
                return new HoleInIce();
            default:
                throw new IllegalStateException("Unknown hazard type: " + type);
        }
    }

    /**
     * Places 20 food items randomly on the grid.
     * Food items cannot occupy the same space as penguins or hazards.
     */
    private void placeFood() {
        List<Position> availablePositions = getEmptyPositions();
        Collections.shuffle(availablePositions);

        for (int i = 0; i < 20 && i < availablePositions.size(); i++) {
            Food food = Food.createRandom();
            grid.set(availablePositions.get(i), food);
        }
    }

    /**
     * Gets all empty positions on the grid.
     *
     * @return List of empty positions
     */
    private List<Position> getEmptyPositions() {
        List<Position> empty = new ArrayList<>();
        for (Position pos : grid.getAllPositions()) {
            if (grid.isEmpty(pos)) {
                empty.add(pos);
            }
        }
        return empty;
    }

    /**
     * Renders the terrain grid to a string in the format specified.
     * Format: Grid with borders and cell contents matching the example.
     *
     * @return String representation of the grid
     */
    public String renderGrid() {
        StringBuilder sb = new StringBuilder();
        String horizontalBorder = "---------------------------------------------------\n";

        for (int row = 0; row < TerrainGrid.GRID_SIZE; row++) {
            sb.append(horizontalBorder);
            sb.append("|");

            for (int col = 0; col < TerrainGrid.GRID_SIZE; col++) {
                Position pos = new Position(row, col);
                ITerrainObject obj = grid.get(pos);

                String cell;
                if (obj == null) {
                    cell = "    "; // Empty: 4 spaces
                } else {
                    String symbol = obj.getDisplaySymbol();
                    // Center the symbol in a 4-character field
                    cell = " " + String.format("%-2s", symbol) + " ";
                }
                sb.append(cell);
                sb.append("|");
            }
            sb.append("\n");
        }
        sb.append(horizontalBorder);

        return sb.toString();
    }

    /**
     * Gets the list of all penguins on the terrain.
     *
     * @return List of penguins
     */
    public List<Penguin> getPenguins() {
        return new ArrayList<>(penguins);
    }

    /**
     * Gets the player's penguin.
     *
     * @return The player's penguin
     */
    public Penguin getPlayerPenguin() {
        return playerPenguin;
    }

    /**
     * Gets the terrain grid.
     *
     * @return The terrain grid
     */
    public TerrainGrid getGrid() {
        return grid;
    }

    /**
     * Gets a penguin by name.
     *
     * @param name The penguin name (e.g., "P1")
     * @return The penguin with that name, or null if not found
     */
    public Penguin getPenguinByName(String name) {
        for (Penguin p : penguins) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Starts the game - main entry point called from main().
     * Initializes terrain, displays initial state, and runs the game loop.
     */
    public void startGame() {
        System.out
                .println("Welcome to Sliding Penguins Puzzle Game App. An 10x10 icy terrain grid is being generated.");
        System.out.println("Penguins, Hazards, and Food items are also being generated. The initial icy terrain grid:");

        initialize();

        System.out.println(renderGrid());
        displayPenguinInfo();

        runGameLoop();

        displayScoreboard();
    }

    /**
     * Displays information about the penguins.
     */
    private void displayPenguinInfo() {
        System.out.println("These are the penguins on the icy terrain:");
        for (Penguin p : penguins) {
            String playerIndicator = (p == playerPenguin) ? " ---> YOUR PENGUIN" : "";
            System.out.println("- Penguin " + p.getName().substring(1) + " (" + p.getName() + "): " +
                    p.getTypeName() + playerIndicator);
        }
    }

    /**
     * Runs the main game loop for 4 turns per penguin.
     */
    private void runGameLoop() {
        final int TOTAL_TURNS = 4;

        for (int turn = 1; turn <= TOTAL_TURNS; turn++) {
            for (Penguin penguin : penguins) {
                if (penguin.isRemoved()) {
                    continue; // Skip removed penguins
                }

                System.out.println("*** Turn " + turn + " – " + penguin.getName() +
                        (penguin == playerPenguin ? " (Your Penguin)" : "") + ":");

                // Check if stunned
                if (penguin.isStunned()) {
                    System.out.println(penguin.getName() + " is stunned and skips this turn.");
                    penguin.setStunned(false); // Remove stun for next turn
                    continue;
                }

                if (penguin == playerPenguin) {
                    handlePlayerTurn(penguin);
                } else {
                    handleAITurn(penguin);
                }

                System.out.println("New state of the grid:");
                System.out.println(renderGrid());
            }
        }
    }

    /**
     * Handles a player's turn with input prompts.
     */
    private void handlePlayerTurn(Penguin penguin) {
        boolean useSpecialAbility = false;

        // Ask about special ability
        if (penguin.canUseSpecialAbility()) {
            System.out.print("Will " + penguin.getName() + " use its special action? Answer with Y or N --> ");
            useSpecialAbility = promptYesNo();
        }

        // Get movement direction
        System.out.print("Which direction will " + penguin.getName() +
                " move? Answer with U (Up), D (Down), L (Left), R (Right) --> ");
        Direction direction = promptDirection();

        executeTurn(penguin, direction, useSpecialAbility);
    }

    /**
     * Handles an AI penguin's turn with random decision making.
     */
    private void handleAITurn(Penguin penguin) {
        // AI has 30% chance to use special ability (except Rockhopper - see below)
        boolean useSpecialAbility = false;
        if (penguin.canUseSpecialAbility() && Math.random() < 0.3) {
            useSpecialAbility = true;
        }

        // Rockhopper automatically uses ability when moving towards hazard
        if (penguin instanceof RockhopperPenguin && penguin.canUseSpecialAbility()) {
            useSpecialAbility = false; // Will be set true if hazard detected
        }

        // Choose direction (prioritize food > non-hole hazards > water)
        Direction direction = chooseAIDirection(penguin);

        // Special case: Rockhopper auto-uses ability when moving towards hazard
        if (penguin instanceof RockhopperPenguin && penguin.canUseSpecialAbility()) {
            Position next = penguin.getPosition().move(direction);
            if (grid.isValidPosition(next) && grid.get(next) instanceof Hazard) {
                useSpecialAbility = true;
                System.out.println(penguin.getName() + " will automatically USE its special action.");
            }
        }

        if (useSpecialAbility && !(penguin instanceof RockhopperPenguin)) {
            System.out.println(penguin.getName() + " chooses to USE its special action.");
        } else if (!useSpecialAbility) {
            System.out.println(penguin.getName() + " does NOT to use its special action.");
        }

        System.out.println(penguin.getName() + " chooses to move to the " + direction.getDisplayName() + ".");

        executeTurn(penguin, direction, useSpecialAbility);
    }

    /**
     * Chooses a direction for AI penguin based on priorities.
     */
    private Direction chooseAIDirection(Penguin penguin) {
        Position pos = penguin.getPosition();
        List<Direction> foodDirections = new ArrayList<>();
        List<Direction> safeDirections = new ArrayList<>();
        List<Direction> hazardDirections = new ArrayList<>();
        List<Direction> waterDirections = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Position next = pos.move(dir);

            if (!next.isWithinBounds(TerrainGrid.GRID_SIZE)) {
                waterDirections.add(dir);
                continue;
            }

            ITerrainObject obj = grid.get(next);
            if (obj instanceof Food) {
                foodDirections.add(dir);
            } else if (obj instanceof HoleInIce && !((HoleInIce) obj).isPlugged()) {
                // Treat unplugged hole as worst option (like water)
                waterDirections.add(dir);
            } else if (obj instanceof Hazard) {
                hazardDirections.add(dir);
            } else {
                safeDirections.add(dir);
            }
        }

        // Prioritize: food > safe > hazards > water
        if (!foodDirections.isEmpty()) {
            return foodDirections.get((int) (Math.random() * foodDirections.size()));
        }
        if (!safeDirections.isEmpty()) {
            return safeDirections.get((int) (Math.random() * safeDirections.size()));
        }
        if (!hazardDirections.isEmpty()) {
            return hazardDirections.get((int) (Math.random() * hazardDirections.size()));
        }
        // No choice but water
        return waterDirections.get((int) (Math.random() * waterDirections.size()));
    }

    /**
     * Executes a penguin's turn with the specified direction and ability usage.
     */
    private void executeTurn(Penguin penguin, Direction direction, boolean useSpecialAbility) {
        CollisionHandler.MovementResult result = collisionHandler.movePenguin(penguin, direction, useSpecialAbility);

        // Display all messages from the movement
        for (String message : result.getMessages()) {
            System.out.println(message);
        }

        // Handle special cases from result
        if (result.penguinBouncedBack) {
            // Penguin bounced back, slide in opposite direction
            CollisionHandler.MovementResult bounceResult = collisionHandler.movePenguin(penguin, result.bounceDirection,
                    false);
            for (String message : bounceResult.getMessages()) {
                System.out.println(message);
            }
        }

        if (result.triggeredPenguinSlide != null) {
            // Another penguin started sliding
            System.out.println(result.triggeredPenguinSlide.getName() + " starts sliding " +
                    result.slideDirection.getDisplayName() + "!");
            CollisionHandler.MovementResult slideResult = collisionHandler.movePenguin(result.triggeredPenguinSlide,
                    result.slideDirection, false);
            for (String message : slideResult.getMessages()) {
                System.out.println(message);
            }
        }
    }

    /**
     * Displays the final scoreboard with rankings.
     */
    private void displayScoreboard() {
        System.out.println("***** GAME OVER *****");
        System.out.println("***** SCOREBOARD FOR THE PENGUINS *****");

        // Sort penguins by total food weight (descending)
        List<Penguin> ranked = new ArrayList<>(penguins);
        ranked.sort(Comparator.comparingInt(Penguin::getTotalFoodWeight).reversed());

        int place = 1;
        for (Penguin penguin : ranked) {
            String playerIndicator = (penguin == playerPenguin) ? " (Your Penguin)" : "";
            System.out.println("* " + getPlaceString(place) + " place: " + penguin.getName() + playerIndicator);

            System.out.print(" |---> Food items: ");
            List<Food> foods = penguin.getFoodInventory();
            if (foods.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < foods.size(); i++) {
                    Food food = foods.get(i);
                    System.out.print(food.getFoodType().getDisplaySymbol() + " (" + food.getWeight() + " units)");
                    if (i < foods.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }

            System.out.println(" |---> Total weight: " + penguin.getTotalFoodWeight() + " units");
            place++;
        }
    }

    /**
     * Converts place number to ordinal string (1st, 2nd, 3rd).
     */
    private String getPlaceString(int place) {
        if (place == 1)
            return "1st";
        if (place == 2)
            return "2nd";
        if (place == 3)
            return "3rd";
        return place + "th";
    }

    /**
     * Prompts user for yes/no input (case-insensitive).
     */
    private boolean promptYesNo() {
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            } else if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.print("Invalid input. Please answer with Y or N --> ");
        }
    }

    /**
     * Prompts user for direction input (case-insensitive).
     */
    private Direction promptDirection() {
        while (true) {
            String input = scanner.nextLine().trim();
            Direction direction = Direction.fromInput(input);
            if (direction != null) {
                return direction;
            }
            System.out.print("Invalid input. Please answer with U (Up), D (Down), L (Left), R (Right) --> ");
        }
    }
}
