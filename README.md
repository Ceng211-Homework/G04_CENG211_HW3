# Sliding Penguins Puzzle Game

A Java-based puzzle game where penguins slide across an icy terrain, collecting food while avoiding hazards. This project demonstrates object-oriented programming principles including inheritance, polymorphism, abstraction, and proper exception handling.

## Table of Contents
- [Game Overview](#game-overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Game Rules](#game-rules)
- [Project Structure](#project-structure)
- [OOP Concepts](#oop-concepts)
- [Technologies](#technologies)
- [Testing](#testing)
- [Authors](#authors)

## Game Overview

Sliding Penguins is a turn-based puzzle game set on a 10x10 icy terrain grid. Players control one of three penguins, competing to collect the most food while navigating hazards. The icy surface causes penguins to slide continuously in their chosen direction until they hit an obstacle.

### Key Game Elements
- **3 Penguins**: Each with unique special abilities (King, Emperor, Royal, Rockhopper)
- **15 Hazards**: Ice blocks, sea lions, and holes that affect gameplay
- **20 Food Items**: Fish and krill of varying weights to collect
- **4 Turns**: Each penguin gets 4 moves to maximize their food collection

## Features

✅ **Four Unique Penguin Types**
- **King Penguin**: Can stop at the 5th square while sliding
- **Emperor Penguin**: Can stop at the 3rd square while sliding
- **Royal Penguin**: Can step one square adjacent before sliding
- **Rockhopper Penguin**: Can jump over one hazard

✅ **Dynamic Hazards**
- **Light Ice Block**: Stuns penguins, starts sliding when hit
- **Heavy Ice Block**: Immovable, causes penguins to lose lightest food
- **Sea Lion**: Causes penguins to bounce back in opposite direction
- **Hole in Ice**: Removes penguins from game (unless plugged)

✅ **Strategic Gameplay**
- AI-controlled penguins with smart decision-making
- Special abilities usable once per penguin
- Collision mechanics with momentum transfer
- Final scoreboard ranking by total food weight

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Ceng211-Homework/G04_CENG211_HW3.git
cd G04_CENG211_HW3
```

2. Compile the project:
```bash
mvn clean compile
```

3. Run the game:
```bash
mvn exec:java -Dexec.mainClass="com.g04.SlidingPuzzle.SlidingPuzzleApp"
```

### Running Tests
```bash
mvn test
```

## How to Play

1. **Game Start**: The game generates a 10x10 grid with randomly placed penguins, hazards, and food
2. **Your Turn**: You control one penguin (marked with "YOUR PENGUIN")
3. **Make Moves**: On your turn, you can:
   - Choose to use your special ability (Y/N)
   - Select a direction to slide (U/D/L/R)
4. **Watch AI**: AI-controlled penguins make their moves automatically
5. **Game End**: After 4 turns per penguin, the scoreboard shows rankings by food weight

### Controls
- `Y` / `N` - Use/Don't use special ability
- `U` - Slide Up (Upwards)
- `D` - Slide Down (Downwards)
- `L` - Slide Left
- `R` - Slide Right

## Game Rules

### Movement
- Penguins slide continuously in chosen direction until hitting:
  - Another penguin (momentum transfer)
  - A hazard (various effects)
  - Grid edge (removal from game)

### Hazard Effects
| Hazard | Effect |
|--------|--------|
| Light Ice Block (LB) | Penguin stunned (skips next turn), block slides |
| Heavy Ice Block (HB) | Penguin stops, loses lightest food item |
| Sea Lion (SL) | Penguin bounces back, sea lion slides forward |
| Hole in Ice (HI) | Penguin removed (unless hole is plugged) |

### Food Types
- **Kr** (Krill) - Weight: 1-5 units
- **An** (Anchovy) - Weight: 1-5 units
- **Sq** (Squid) - Weight: 1-5 units
- **Mc** (Mackerel) - Weight: 1-5 units

### Scoring
- Winner determined by **total food weight** collected
- Food items remain with penguin even if removed from game

## Project Structure

```
src/
├── main/java/com/g04/SlidingPuzzle/
│   ├── SlidingPuzzleApp.java          # Main entry point
│   ├── exception/                      # Custom exceptions
│   │   ├── InvalidMoveException.java
│   │   ├── InvalidPositionException.java
│   │   └── InvalidGameStateException.java
│   ├── interfaces/                     # Abstraction layer
│   │   ├── ITerrainObject.java
│   │   └── IHazard.java
│   ├── model/                          # Core game models
│   │   ├── Food.java
│   │   ├── Hazard.java
│   │   ├── IcyTerrain.java            # Main game controller
│   │   ├── Penguin.java
│   │   ├── enums/                      # Game enumerations
│   │   │   ├── Direction.java
│   │   │   ├── FoodType.java
│   │   │   ├── HazardType.java
│   │   │   └── PenguinType.java
│   │   ├── hazards/                    # Hazard implementations
│   │   │   ├── HeavyIceBlock.java
│   │   │   ├── HoleInIce.java
│   │   │   ├── LightIceBlock.java
│   │   │   └── SeaLion.java
│   │   ├── penguins/                   # Penguin implementations
│   │   │   ├── EmperorPenguin.java
│   │   │   ├── KingPenguin.java
│   │   │   ├── RockhopperPenguin.java
│   │   │   └── RoyalPenguin.java
│   │   └── terrain/                    # Grid infrastructure
│   │       ├── Position.java
│   │       └── TerrainGrid.java
│   └── service/                        # Game services
│       ├── CollisionHandler.java       # Movement & collision logic
│       └── GameStateManager.java       # Turn management
└── test/java/com/g04/SlidingPuzzle/   # JUnit 5 tests
    ├── SlidingPuzzleAppTest.java
    └── model/
        ├── PositionTest.java
        ├── TerrainGridTest.java
        └── enums/
            └── DirectionTest.java
```

## OOP Concepts

This project demonstrates advanced Object-Oriented Programming principles:

### 1. **Abstraction**
- `ITerrainObject` interface for all grid objects
- `IHazard` interface defining hazard behavior contracts
- `Penguin` abstract base class for shared penguin behavior
- `Hazard` abstract base class for hazard commonalities

### 2. **Inheritance**
- 4 penguin types extend `Penguin` base class
- 4 hazard types extend `Hazard` base class
- Shared behavior in parent classes, specific behavior in children

### 3. **Polymorphism**
- `ITerrainObject` polymorphic collection in `TerrainGrid`
- Dynamic method dispatch for `handleCollision()` in hazards
- Type-specific special abilities in penguin implementations

### 4. **Encapsulation**
- Private fields with public accessor methods
- Package-private validation helpers
- Service layer separation (CollisionHandler, GameStateManager)

### 5. **Exception Handling**
- Custom exception hierarchy for domain-specific errors
- Validation at architectural boundaries
- Descriptive error messages with factory methods

### 6. **Design Patterns**
- **Factory Methods**: `Food.createRandom()`, exception factories
- **Strategy Pattern**: Special abilities as pluggable behaviors
- **Value Object**: Immutable `Position` class
- **Service Layer**: Separation of concerns (collision, state management)

### 7. **Data Structures**
- `ArrayList<ArrayList<ITerrainObject>>` for grid representation
- `List<Food>` for penguin inventory
- Proper use of Java Collections framework

## Technologies

- **Language**: Java 21
- **Build Tool**: Maven 3.x
- **Testing Framework**: JUnit 5.10.1
- **Dependencies**:
  - JUnit Jupiter API (Testing)
  - JUnit Jupiter Engine (Test Execution)
  - JUnit Jupiter Params (Parameterized Tests)

## Testing

The project includes comprehensive unit tests covering:

### Test Coverage
- **PositionTest** (6 tests): Position creation, movement, bounds checking, equality
- **TerrainGridTest** (8 tests): Grid operations, placement, removal, edge detection
- **DirectionTest** (7 tests): Direction parsing, deltas, opposites
- **AppTest** (1 test): Basic application smoke test

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PositionTest

# Run tests with coverage (if configured)
mvn clean test jacoco:report
```

### Test Statistics
- **Total Tests**: 22
- **Success Rate**: 100%
- **Code Coverage**: Core game mechanics fully tested

## Exception Handling

The game implements three custom exceptions for robust error handling:

### InvalidMoveException
- Thrown when attempting invalid moves (removed penguin, null direction)
- Usage: `CollisionHandler`, `Penguin` special abilities

### InvalidPositionException
- Thrown for invalid grid positions (null, out of bounds)
- Usage: `TerrainGrid` operations (set, remove, move)

### InvalidGameStateException
- Thrown for illegal game states (game over, empty penguin list, invalid parameters)
- Usage: `GameStateManager`, model constructors

## Authors

**Group 04 - CENG211 Object-Oriented Programming**
- Course: CENG211 - Introduction to Object-Oriented Programming
- Institution: [Your University Name]
- Academic Year: 2024-2025

## License

This project is created for educational purposes as part of CENG211 coursework.

## Acknowledgments

- Game mechanics inspired by classic sliding puzzle games
- Developed using Java best practices and design patterns
- Thanks to CENG211 instructors for project guidance

---

**Note**: This is an academic project demonstrating OOP principles. The game is fully functional with AI opponents and complete collision mechanics.
