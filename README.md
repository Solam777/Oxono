# Oxono

Oxono is a board strategy game developed in **Java** with **JavaFX**. It is inspired by tic-tac-toe but introduces extended mechanics that add more depth and complexity. A player wins when they align **4 pieces of their color** or **4 pieces with the same symbol (X or O)**, regardless of color. If no alignment is possible and all pieces have been placed, the game ends in a draw.

---

## Author

Tijani Solam
---

## Project Description

The project demonstrates game logic, modular design, and clean code structure. It integrates advanced features such as undo/redo, AI opponents, and totem movement mechanics. The objective is to align symbols on the board while anticipating the opponent’s moves.

---

## Features

* Graphical interface built with **JavaFX**
* Totem movement
* Pawn placement
* Move management with history:

  * Undo (cancel last move)
  * Redo (replay a canceled move)
  * Forfeit
* Play against a bot with a random strategy
* Endgame detection:

  * Victory (alignment)
  * Draw (no moves possible)
  * Forfeit

---

## Choice of Architecture

The project follows the **Model-View-Controller (MVC)** architecture, ensuring a clear separation of concerns between the game logic, interface, and control flow.

---

## Design Patterns

Several design patterns are used to ensure maintainability and clean architecture:

* **Observer** – Updates the game view whenever the model changes.
* **Command** – Manages user actions and provides undo/redo functionality.
* **Strategy** – Allows interchangeable AI behaviors (random or more advanced approaches).

---

## Artificial Intelligence

Currently, the game includes a bot using a random strategy. The architecture is open to improvements, such as implementing a **Negamax search algorithm with bitboard representation** for efficient state evaluation and better AI decision-making.

---

## How to Play

1. Each player has pawns (X or O, with distinct colors).
2. On their turn, a player can:

   * Place a pawn on an empty square.
   * Move their totem according to the rules.
3. The first player to align 4 identical symbols (X or O) or 4 pawns of their color wins the game.
4. If all pawns are placed and no alignment is made, the match ends in a draw.

The **Undo/Redo** features allow players to step back or replay moves, and the bot can be challenged for practice.

---

## Preview


### Game Interface

<img width="1855" height="1010" alt="image" src="https://github.com/user-attachments/assets/798158a2-bc0f-44b2-b617-e0ce40866908" />

### Gameplay Video

[Watch the gameplay demonstration](https://github.com/user-attachments/assets/c184bd35-8453-479c-9817-4dfa4bedc9ec)

---

## Class Diagram

![UML Class Diagram](A_UML_class_diagram_visualizes_the_relationships_a.png)

---

## Installation and Usage

To run the application:

1. Clone this repository:

   ```bash
   git clone https://github.com/Solam777/oxono.git
   ```

2. Navigate to the project directory:

   ```bash
   cd oxono/oxono
   ```

3. Start the project using Maven:

   ```bash
   mvn javafx:run
   ```

Make sure you have **Maven** and **Java** installed.

---




  
