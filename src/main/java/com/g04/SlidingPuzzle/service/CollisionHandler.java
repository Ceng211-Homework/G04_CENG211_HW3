package com.g04.SlidingPuzzle.service;

import com.g04.SlidingPuzzle.exception.InvalidMoveException;
import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.*;
import com.g04.SlidingPuzzle.model.enums.Direction;
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
import java.util.List;

/**
 * Handles movement and collision logic for objects on the icy terrain.
 * This is a helper class that coordinates sliding, pathfinding, and collision resolution.
 */
public class CollisionHandler {
    private final TerrainGrid grid;

    /**
     * Creates a new collision handler for the specified grid.
     *
     * @param grid The terrain grid to manage collisions for
     */
    public CollisionHandler(TerrainGrid grid) {
        this.grid = grid;
    }

    /**
     * Moves a penguin in the specified direction, handling all collisions and special abilities.
     *
     * @param penguin The penguin to move
     * @param direction The direction to move
     * @param useSpecialAbility Whether the penguin is using its special ability
     * @return Movement result describing what happened
     * @throws InvalidMoveException if penguin is removed or direction is null
     */
    public MovementResult movePenguin(Penguin penguin, Direction direction, boolean useSpecialAbility) {
        // Validate penguin can move
        if (penguin.isRemoved()) {
            throw InvalidMoveException.penguinRemoved(penguin.getName());
        }
        if (direction == null) {
            throw new InvalidMoveException("Direction cannot be null");
        }

        MovementResult result = new MovementResult(penguin);
        Position startPos = penguin.getPosition();

        // Handle RoyalPenguin special ability (adjacent step before sliding)
        if (useSpecialAbility && penguin instanceof RoyalPenguin && penguin.canUseSpecialAbility()) {
            penguin.useSpecialAbility();
            Position adjacentPos = startPos.move(direction);

            if (!adjacentPos.isWithinBounds(TerrainGrid.GRID_SIZE)) {
                // Fell off edge
                penguin.remove();
                grid.remove(startPos);
                result.fellOffEdge = true;
                result.finalPosition = null;
                result.addMessage(penguin.getName() + " steps off the grid and falls into the water!");
                result.addMessage("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
                return result;
            }

            // Move to adjacent square
            grid.move(startPos, adjacentPos);
            result.finalPosition = adjacentPos;
            result.addMessage(penguin.getName() + " moves one square to the " + direction.getDisplayName() + ".");
            startPos = adjacentPos; // Continue from new position
        }

        // Handle RockhopperPenguin special ability (prepare jump)
        if (useSpecialAbility && penguin instanceof RockhopperPenguin && penguin.canUseSpecialAbility()) {
            RockhopperPenguin rockhopper = (RockhopperPenguin) penguin;
            rockhopper.useSpecialAbility();
            rockhopper.prepareJump(direction);
            result.addMessage(penguin.getName() + " will automatically USE its special action.");
        }

        // Calculate sliding path
        List<Position> path = calculateSlidingPath(penguin, startPos, direction, useSpecialAbility);

        // Execute slide along path
        slideAlongPath(penguin, path, direction, result);

        return result;
    }

    /**
     * Calculates the sliding path for a penguin in the specified direction.
     *
     * @param penguin The penguin sliding
     * @param start The starting position
     * @param direction The direction of movement
     * @param usingSpecialAbility Whether special ability is active
     * @return List of positions in the sliding path
     */
    private List<Position> calculateSlidingPath(Penguin penguin, Position start, Direction direction,
                                                 boolean usingSpecialAbility) {
        List<Position> path = new ArrayList<>();
        Position current = start;
        int squareCount = 0;

        // Check for King/Emperor special ability stop condition
        int stopAtSquare = -1;
        if (usingSpecialAbility && penguin instanceof KingPenguin && penguin.canUseSpecialAbility()) {
            stopAtSquare = ((KingPenguin) penguin).getStopSquare();
        } else if (usingSpecialAbility && penguin instanceof EmperorPenguin && penguin.canUseSpecialAbility()) {
            stopAtSquare = ((EmperorPenguin) penguin).getStopSquare();
        }

        while (true) {
            Position next = current.move(direction);
            squareCount++;

            // Check boundary
            if (!next.isWithinBounds(TerrainGrid.GRID_SIZE)) {
                break; // Will fall off edge
            }

            // Check for obstacles
            ITerrainObject obstacle = grid.get(next);

            // Food doesn't stop sliding - collect and continue
            if (obstacle instanceof Food) {
                path.add(next);
                current = next;
                continue;
            }

            // Check for Rockhopper jump over hazard
            if (penguin instanceof RockhopperPenguin) {
                RockhopperPenguin rockhopper = (RockhopperPenguin) penguin;
                if (rockhopper.isJumpPrepared() && obstacle instanceof Hazard) {
                    Position landingPos = next.move(direction);
                    if (landingPos.isWithinBounds(TerrainGrid.GRID_SIZE) && grid.isEmpty(landingPos)) {
                        // Successful jump
                        path.add(landingPos);
                        current = landingPos;
                        rockhopper.clearJump();
                        continue;
                    } else {
                        // Failed jump - will collide with hazard
                        rockhopper.clearJump();
                    }
                }
            }

            // Obstacle encountered
            if (obstacle != null) {
                break; // Collision
            }

            // Empty square
            path.add(next);
            current = next;

            // Check special ability stop condition
            if (stopAtSquare > 0 && squareCount == stopAtSquare) {
                break; // King/Emperor stops at specific square
            }
        }

        return path;
    }

    /**
     * Slides the penguin along the calculated path, handling collisions and food collection.
     *
     * @param penguin The penguin sliding
     * @param path The path to slide along
     * @param direction The direction of movement
     * @param result The movement result to update
     */
    private void slideAlongPath(Penguin penguin, List<Position> path, Direction direction, MovementResult result) {
        Position startPos = penguin.getPosition();

        if (path.isEmpty()) {
            // Immediate collision or boundary
            Position next = startPos.move(direction);

            if (!next.isWithinBounds(TerrainGrid.GRID_SIZE)) {
                // Fell off edge
                penguin.remove();
                grid.remove(startPos);
                result.fellOffEdge = true;
                result.finalPosition = null;
                result.addMessage(penguin.getName() + " falls into the water!");
                result.addMessage("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
            } else {
                // Immediate collision
                ITerrainObject obstacle = grid.get(next);
                handleCollision(penguin, startPos, obstacle, direction, result);
            }
            return;
        }

        // Slide through path, collecting food
        Position currentPos = startPos;
        for (Position pos : path) {
            ITerrainObject obj = grid.get(pos);
            if (obj instanceof Food) {
                Food food = (Food) obj;
                penguin.collectFood(food);
                grid.remove(pos);
                result.addMessage(penguin.getName() + " takes the " + food.getFoodType().getDisplaySymbol() +
                                  " on the ground. (Weight=" + food.getWeight() + " units)");
            }
            currentPos = pos;
        }

        // Move penguin to final position in path
        grid.move(startPos, currentPos);
        result.finalPosition = currentPos;

        // Check if there's an obstacle beyond the path (collision at end)
        Position beyond = currentPos.move(direction);
        if (!beyond.isWithinBounds(TerrainGrid.GRID_SIZE)) {
            // Slides off edge at end
            penguin.remove();
            grid.remove(currentPos);
            result.fellOffEdge = true;
            result.finalPosition = null;
            result.addMessage(penguin.getName() + " slides off the edge and falls into the water!");
            result.addMessage("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
        } else {
            ITerrainObject obstacle = grid.get(beyond);
            if (obstacle != null) {
                handleCollision(penguin, currentPos, obstacle, direction, result);
            }
        }
    }

    /**
     * Handles collision between a penguin and an obstacle.
     *
     * @param penguin The colliding penguin
     * @param penguinPos The penguin's current position
     * @param obstacle The obstacle hit
     * @param direction The direction of movement
     * @param result The movement result to update
     */
    private void handleCollision(Penguin penguin, Position penguinPos, ITerrainObject obstacle,
                                  Direction direction, MovementResult result) {
        if (obstacle instanceof Penguin) {
            handlePenguinPenguinCollision(penguin, (Penguin) obstacle, direction, result);
        } else if (obstacle instanceof LightIceBlock) {
            handleLightIceBlockCollision(penguin, penguinPos, (LightIceBlock) obstacle, direction, result);
        } else if (obstacle instanceof HeavyIceBlock) {
            handleHeavyIceBlockCollision(penguin, result);
        } else if (obstacle instanceof SeaLion) {
            handleSeaLionCollision(penguin, penguinPos, (SeaLion) obstacle, direction, result);
        } else if (obstacle instanceof HoleInIce) {
            handleHoleInIceCollision(penguin, penguinPos, (HoleInIce) obstacle, result);
        }
    }

    /**
     * Handles collision between two penguins (momentum transfer).
     */
    private void handlePenguinPenguinCollision(Penguin movingPenguin, Penguin stationaryPenguin,
                                               Direction direction, MovementResult result) {
        result.addMessage(movingPenguin.getName() + " collides with " + stationaryPenguin.getName() +
                          ". Momentum is transferred.");
        // The stationary penguin now slides in the same direction
        // This would trigger a new movement for the stationary penguin
        result.triggeredPenguinSlide = stationaryPenguin;
        result.slideDirection = direction;
    }

    /**
     * Handles collision with a Light Ice Block (penguin stunned, block slides).
     */
    private void handleLightIceBlockCollision(Penguin penguin, Position penguinPos, LightIceBlock block,
                                              Direction direction, MovementResult result) {
        penguin.setStunned(true);
        result.addMessage(penguin.getName() + " hits a Light Ice Block and is temporarily stunned!");
        result.addMessage(penguin.getName() + "'s next turn will be skipped.");

        // Block starts sliding
        Position blockPos = block.getPosition();
        slideHazard(block, blockPos, direction, result);
    }

    /**
     * Handles collision with a Heavy Ice Block (penguin loses lightest food).
     */
    private void handleHeavyIceBlockCollision(Penguin penguin, MovementResult result) {
        Food lostFood = penguin.removeLightestFood();
        if (lostFood != null) {
            result.addMessage(penguin.getName() + " hits a Heavy Ice Block and loses " +
                              lostFood.getFoodType().getDisplaySymbol() + " (" + lostFood.getWeight() + " units)!");
        } else {
            result.addMessage(penguin.getName() + " hits a Heavy Ice Block but carries no food.");
        }
    }

    /**
     * Handles collision with a Sea Lion (penguin bounces back, lion slides forward).
     */
    private void handleSeaLionCollision(Penguin penguin, Position penguinPos, SeaLion lion,
                                       Direction direction, MovementResult result) {
        result.addMessage(penguin.getName() + " hits a Sea Lion and bounces back!");

        // Lion slides in original direction
        Position lionPos = lion.getPosition();
        slideHazard(lion, lionPos, direction, result);

        // Penguin bounces in opposite direction
        Direction opposite = direction.getOpposite();
        result.addMessage(penguin.getName() + " slides " + opposite.getDisplayName() + "!");
        // This would trigger a new slide for the penguin
        result.penguinBouncedBack = true;
        result.bounceDirection = opposite;
    }

    /**
     * Handles collision with a Hole in Ice (penguin removed or hole plugged).
     */
    private void handleHoleInIceCollision(Penguin penguin, Position penguinPos, HoleInIce hole,
                                         MovementResult result) {
        if (hole.isPlugged()) {
            result.addMessage(penguin.getName() + " passes over a plugged hole.");
        } else {
            penguin.remove();
            grid.remove(penguinPos);
            result.fellIntoHole = true;
            result.finalPosition = null;
            result.addMessage(penguin.getName() + " falls into a Hole in Ice!");
            result.addMessage("*** " + penguin.getName() + " IS REMOVED FROM THE GAME!");
        }
    }

    /**
     * Slides a hazard in the specified direction until it hits an obstacle or falls off.
     */
    private void slideHazard(Hazard hazard, Position start, Direction direction, MovementResult result) {
        Position current = start;

        while (true) {
            Position next = current.move(direction);

            // Check boundary
            if (!next.isWithinBounds(TerrainGrid.GRID_SIZE)) {
                grid.remove(current);
                result.addMessage(hazard.getDisplaySymbol() + " slides off the edge.");
                return;
            }

            ITerrainObject obstacle = grid.get(next);

            // Empty square - continue sliding
            if (obstacle == null) {
                grid.move(current, next);
                current = next;
                continue;
            }

            // Hit food - remove food and continue
            if (obstacle instanceof Food) {
                grid.remove(next);
                grid.move(current, next);
                result.addMessage(hazard.getDisplaySymbol() + " destroys food at " + next);
                current = next;
                continue;
            }

            // Hit hole - plug it
            if (obstacle instanceof HoleInIce) {
                HoleInIce hole = (HoleInIce) obstacle;
                if (!hole.isPlugged()) {
                    hole.plug();
                    grid.remove(current);
                    result.addMessage(hazard.getDisplaySymbol() + " falls into a hole and plugs it!");
                    return;
                }
            }

            // Hit something else - stop
            result.addMessage(hazard.getDisplaySymbol() + " stops at " + current);
            return;
        }
    }

    /**
     * Result object for movement operations.
     */
    public static class MovementResult {
        public Penguin penguin;
        public Position finalPosition;
        public boolean fellOffEdge;
        public boolean fellIntoHole;
        public boolean penguinBouncedBack;
        public Direction bounceDirection;
        public Penguin triggeredPenguinSlide;
        public Direction slideDirection;
        private List<String> messages;

        public MovementResult(Penguin penguin) {
            this.penguin = penguin;
            this.messages = new ArrayList<>();
        }

        public void addMessage(String message) {
            messages.add(message);
        }

        public List<String> getMessages() {
            return messages;
        }
    }
}
