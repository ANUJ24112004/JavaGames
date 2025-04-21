# Shape RPG Arena Game

A fast-paced 2D arena game where characters are represented by colored geometric shapes.

## Features

- **Multiple Game Modes**:
  - Campaign Mode (15 progressive levels with unique challenges)
  - Endless Mode (Wave-based survival with increasing difficulty)
  - PvP Mode (2 players battle each other and enemies)

- **Campaign Mode Features**:
  - 15 challenging levels with progressively difficult enemies
  - Unique themes for level ranges (1-5, 6-10, 11-15)
  - Epic boss battles every 5 levels with special effects
  - Final boss with spectacular visuals

- **Endless Mode Features**:
  - Wave-based enemy spawning
  - Difficulty scales with your score
  - Mini-bosses appear periodically
  - Play until you're overwhelmed

- **PvP Mode Features**:
  - Two players can battle each other while also fighting enemies
  - Super durable players with 10x normal health for extended battles
  - Percentage-based damage system (3% health per hit, 6% with attack boost)
  - Strategic power-up collection to gain advantage
  - Competitive gameplay with different colored attack effects

- **Visual Effects**:
  - Animated text announcements for levels, bosses, and waves
  - Dynamic combat animations
  - Particle effects for collisions and power-ups

- **Combat System**:
  - Omnidirectional attacks hit all enemies within range
  - Attack animations with growing/shrinking and fading effects
  - Health bars with smooth drain/gain animations
  - Different colored attack effects based on power-ups

- **Power-ups**:
  - Health (Green Square)
  - Speed (Yellow Triangle)
  - Attack (Orange Star) - Doubles damage in all modes
  - Defense (Purple Hexagon)

- **Controls**:
  - Game displays a controls screen before starting
  - Player 1: WASD to move, Space to attack
  - Player 2: Arrow keys to move, Enter to attack
  - P: Pause
  - ESC: Return to main menu

## Game Flow

1. Main menu with game mode selection
2. Controls screen showing all key bindings
3. Game starts after player acknowledges controls

## How to Run

Make sure you have Java installed, then compile and run the game:

```
javac src/*.java
java -cp src Main
```

## Development Notes

- Built using Java Swing for graphics
- No external libraries required
- Character movement uses simple 2D physics
- Enemies have basic AI that follows the player
- Smooth animations for all game effects 