package com.g04.SlidingPuzzle.model;

import com.g04.SlidingPuzzle.exception.InvalidGameStateException;
import com.g04.SlidingPuzzle.interfaces.ITerrainObject;
import com.g04.SlidingPuzzle.model.enums.FoodType;
import com.g04.SlidingPuzzle.model.terrain.Position;

/**
 * Represents a food item on the terrain grid.
 * Food items have a type and weight, and can be collected by penguins.
 */
public class Food implements ITerrainObject {

    private final FoodType foodType;
    private final int weight;
    private Position position;

    /**
     * Creates a new food item with the specified type and weight.
     *
     * @param foodType The type of food
     * @param weight The weight in units (1-5)
     * @throws InvalidGameStateException if foodType is null or weight is invalid
     */
    public Food(FoodType foodType, int weight) {
        if (foodType == null) {
            throw InvalidGameStateException.nullParameter("foodType");
        }
        if (weight < 1 || weight > 5) {
            throw InvalidGameStateException.invalidFoodWeight(weight);
        }
        this.foodType = foodType;
        this.weight = weight;
    }

    /**
     * Creates a random food item with random type and weight (1-5 units).
     *
     * @return A new random Food object
     */
    public static Food createRandom() {
        FoodType type = FoodType.random();
        int weight = 1 + (int) (Math.random() * 5); // 1-5 units
        return new Food(type, weight);
    }

    /**
     * Gets the weight of this food item.
     *
     * @return The weight in units
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Gets the type of this food item.
     *
     * @return The food type
     */
    public FoodType getFoodType() {
        return foodType;
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
        return foodType.getDisplaySymbol();
    }

    @Override
    public boolean canSlide() {
        return false; // Food items do not slide
    }

    @Override
    public String toString() {
        return foodType.getDisplaySymbol() + " (" + weight + " units)";
    }
}
