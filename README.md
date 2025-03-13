# Chess Engine in Java

## Project Overview
This project is a **chess engine** written in **Java**, designed for a university software development course. The engine follows standard chess rules and allows users to play against another player.

## Features
- **Full Chess Game Implementation**: Supports all standard chess rules, including castling, en passant, and pawn promotion.
- **Graphical User Interface (GUI)**: A simple yet effective Swing-based GUI for easy interaction.
- **Move Validation**: Ensures only legal moves are played.

## Technologies Used
- **Java (JDK 11+)**
- **Swing (for GUI)**

## Installation
1. **Download the JAR executable**:
    - Navigate to the [Releases](https://github.com/AleksDw/ChessEngine/releases) page.
    - Download the latest `ChessEngine.jar` file.
2. **Run the application**:
   ```sh
   java -jar ChessEngine.jar
   ```

## Usage
- Run the program.
- Play using the GUI.
- To move a piece, hold and hover over a highlighted square before releasing.

## File Structure
```
ChessEngine/
│── src/
│   ├── chess/
│   │   ├── board/
│   │   │   ├── Main.java           # Entry point
│   │   │   ├── Board.java          # Chessboard representation
│   │   │   ├── CheckScanner.java   # Handles check validation
│   │   │   ├── Input.java          # Handles user input
│   │   ├── pieces/
│   │   │   ├── Piece.java          # Base class for pieces
│   │   │   ├── Bishop.java         # Bishop piece logic
│   │   │   ├── King.java           # King piece logic
│   │   │   ├── Knight.java         # Knight piece logic
│   │   │   ├── Pawn.java           # Pawn piece logic
│   │   │   ├── Queen.java          # Queen piece logic
│   │   │   ├── Rook.java           # Rook piece logic
│   ├── res/
│   │   ├── chessPieces.png         # Chess piece sprites (from CleanPNG)
│── README.md                       # Documentation
```
Chess piece sprites is from [CleanPNG](https://www.cleanpng.com/png-chess-piece-pin-knight-clip-art-chess-pieces-585651/)

## Future Improvements
- Implement AI using **min-max algorithm**.
- Improve **GUI aesthetics** with a better chessboard design.

## Contributor
- **Aleks Dworniczak** - [GitHub](https://github.com/AleksDw)
