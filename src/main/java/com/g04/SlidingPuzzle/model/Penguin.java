package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.exception.InvalidGameStateException;
import com.g04.SlidingPuzzle.exception.InvalidMoveException;
import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.Direction;
import com.g04.SlidingPuzzle.model.enums.PenguinType;
import com.g04.SlidingPuzzle.model.terrain.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Abstract base class for all penguin types.
 * Implements common penguin behaviors including movement, food collection, and special abilities.
 */
public abstract class Penguin implements ITerrainObject {
    private final String name;
    private Position position;
    private final List<Food> foodInventory;
    private boolean specialAbilityUsed;
    private boolean isStunned;
    private boolean isRemoved;

    /**
     * Creates a new penguin with the specified name.
     *
     * @param name The penguin's identifier (e.g., "P1", "P2", "P3")
     * @throws InvalidGameStateException if name is null or empty
     */
    public Penguin(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw InvalidGameStateException.nullParameter("name");
        }
        this.name = name;
        this.foodInventory = new ArrayList<>();
        this.specialAbilityUsed = false;
        this.isStunned = false;
        this.isRemoved = false;
    }

    /**
     * Gets the penguin's name/identifier.
     *
     * @return The penguin name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the penguin's type.
     *
     * @return The penguin type enum
     */
    public abstract PenguinType getPenguinType();

    /**
     * Gets the penguin's type name for display.
     *
     * @return The type name (e.g., "King Penguin", "Emperor Penguin")
     */
    public String getTypeName() {
        return getPenguinType().getDisplayName();
    }

    /**
     * Checks if this penguin can use its special ability.
     * Subclasses can override to add specific conditions.
     *
     * @return true if special ability can be used, false otherwise
     */
    public boolean canUseSpecialAbility() {
        return !specialAbilityUsed;
    }

    /**
     * Marks the special ability as used.
     *
     * @throws InvalidMoveException if ability has already been used or penguin is removed
     */
    public void useSpecialAbility() {
        if (isRemoved) {
            throw InvalidMoveException.penguinRemoved(name);
        }
        if (specialAbilityUsed) {
            throw new InvalidMoveException(
                String.format("%s cannot use special ability - already used", name)
            );
        }
        this.specialAbilityUsed = true;
    }

    /**
     * Checks if the special ability has been used.
     *
     * @return true if ability used, false otherwise
     */
    public boolean hasUsedSpecialAbility() {
        return specialAbilityUsed;
    }

    /**
     * Adds a food item to this penguin's inventory.
     *
     * @param food The food item to collect
     * @throws InvalidGameStateException if food is null
     * @throws InvalidMoveException if penguin is removed
     */
    public void collectFood(Food food) {
        if (isRemoved) {
            throw InvalidMoveException.penguinRemoved(name);
        }
        if (food == null) {
            throw InvalidGameStateException.nullParameter("food");
        }
        foodInventory.add(food);
    }

    /**
     * Removes and returns the lightest food item from inventory.
     * Used as penalty when hitting HeavyIceBlock.
     *
     * @return The removed food item, or null if inventory is empty
     */
    public Food removeLightestFood() {
        if (foodInventory.isEmpty()) {
            return null;
        }
        foodInventory.sort(Comparator.comparingInt(Food::getWeight));
        return foodInventory.remove(0);
    }

    /**
     * Gets the total weight of all collected food.
     *
     * @return The total weight in units
     */
    public int getTotalFoodWeight() {
        return foodInventory.stream().mapToInt(Food::getWeight).sum();
    }

    /**
     * Gets the list of collected food items.
     *
     * @return The food inventory
     */
    public List<Food> getFoodInventory() {
        return new ArrayList<>(foodInventory);
    }

    /**
     * Checks if this penguin is stunned (skips next turn).
     *
     * @return true if stunned, false otherwise
     */
    public boolean isStunned() {
        return isStunned;
    }

    /**
     * Sets the stunned state of this penguin.
     *
     * @param stunned true to stun the penguin, false to remove stun
     */
    public void setStunned(boolean stunned) {
        this.isStunned = stunned;
    }

    /**
     * Checks if this penguin has been removed from the game.
     *
     * @return true if removed, false otherwise
     */
    public boolean isRemoved() {
        return isRemoved;
    }

    /**
     * Marks this penguin as removed from the game.
     */
    public void remove() {
        this.isRemoved = true;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String getDisplaySymbol() {
        return name;
    }

    @Override
    public boolean canSlide() {
        return true; // All penguins can slide
    }

    @Override
    public String toString() {
        return name + " (" + getTypeName() + ")";
    }
}

