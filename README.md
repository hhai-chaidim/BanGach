# BanGach

_A simple brick-breaking game built with Java_

![Last Commit](https://img.shields.io/github/last-commit/hhai-chaidim/BanGach?style=flat-square)
![Language](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white)

---

## Table of Contents
- [Overview](#overview)
- [Game Concept](#game-concept)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Usage](#usage)
- [Controls](#controls)

---

## Overview

**BanGach** is a simple **brick-breaking (Breakout-style)** game created with **Java**.  
The player controls a paddle to bounce a ball and break all the bricks on the screen without letting the ball fall.  
This project is designed to practice basic **Java OOP**, **graphics (Swing/AWT)**, and **game loop** implementation.

---

## Game Concept

- The game window displays a paddle, ball, and multiple rows of bricks.  
- The player uses the keyboard to move the paddle left and right.  
- Each time the ball hits a brick, that brick disappears, and the player earns points.  
- The game ends when:
  - All bricks are destroyed (you win 🎉)
  - The ball falls below the paddle (you lose 💀)

---

## Getting Started

### Prerequisites

- **Java JDK 8 or higher**
- Any **IDE** that supports Java (e.g., IntelliJ IDEA, Eclipse, VS Code with Java extensions)

### Installation

```bash
git clone https://github.com/hhai-chaidim/BanGach.git
cd BanGach
```

### Usage

To run the game:

```bash
javac -d bin src/*.java
java -cp bin Main
```

*(Replace `Main` with the actual main class if different, e.g., `GameFrame` or `BanGachGame`)*

---

## Controls

| Key | Action |
|-----|---------|
| A / D | Move paddle left or right |
| Space | Start |
| P | Pause |
| Esc | Exit |


---

[⬆ Return to Top](#bangach)
