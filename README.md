# SiliconValleyTrail

A console-based resource management game where you guide a startup team from San Jose to San Francisco while balancing cash, morale, coffee, and bugs.

---

## Demo

Here is a short demo of the application running:

[Watch Demo Video](https://www.loom.com/share/b4bd85543c3141a5bf389171a7c8d23a)

---

## Tech Stack

- Java  
- Maven  
- Open-Meteo API  

---

## Quick Start

### Prerequisites
- Java 17+  
- Maven  

### Clone the repository
```bash
git clone https://github.com/c-shawn710/SiliconValleyTrail.git
cd SiliconValleyTrail
```

### Run the application
```bash
mvn compile exec:java -Dexec.mainClass="App.Main"
```

---

## Running Tests

Run all unit tests:

```bash
mvn test
```

---

## Architecture Overview

The project is organized into the following packages:

- `core` – main game loop and orchestration (`GameEngine`)
- `ui` – console input/output (`ConsoleUI`)
- `model` – game state and domain objects (`GameState`, `Event`, `EventChoice`, `Route`, `Location`, `WeatherEffect`)
- `service` – gameplay logic and integrations (`GameRules`, `WeatherService`, `EventResolver`, `SaveService`)
- `constants` / `enums` – shared configuration values and types

### Key Components

- **GameEngine** – orchestrates the application and game loop  
- **GameState** – stores all mutable game data  
- **GameRules** – handles win/lose conditions and end-of-day logic  
- **WeatherService** – fetches and maps weather data to gameplay effects  
- **EventResolver** – selects events based on location and weather  

---

## Example Gameplay Flow

1. Start a new game  
2. Each day:
   - View current state (resources + location)  
   - Choose an action (travel, rest, work, save, quit)  
3. Traveling triggers:
   - Weather effects  
   - A location-based event  
4. Events present choices that modify resources  
5. Game ends when:
   - You reach San Francisco (win), or  
   - A losing condition is met (cash, morale, bugs, or coffee exhaustion)  

---

## API Usage & Error Handling

This project uses the Open-Meteo API to fetch real-time weather data.
Open-Meteo was chosen because:

- It does not require an API key
- It provides simple weather data suitable for gameplay
- Weather data is mapped into gameplay effects (morale, coffee, bugs)
  
Weather responses are mapped into a `WeatherEffect` model so the rest of the game does not depend directly on the API response format.

---

### Error Handling

If the API request fails (timeout, network error, non-200 response):

- The game falls back to a **neutral weather effect**  
- Gameplay continues without interruption  

This ensures the game remains playable even when the external API is unavailable.

---

## Decency & Safety

- No personal user data is collected or stored  
- No secrets or API keys are hard-coded  
- External API failures are handled gracefully with fallback behavior  

---

## Design Notes

### Game Loop & Balance

The game is structured around a turn-based loop:

- Player actions modify state  
- Weather and events introduce variability  
- End-of-day rules enforce constraints (e.g., coffee exhaustion)  

The goal was to balance simplicity with meaningful decision-making.

---

### Data Modeling

- `GameState` is the single source of truth for all mutable state  
- Resource values are clamped to maintain valid bounds  
- Events and choices are modeled as data objects for clarity and extensibility  

---

### Error Handling Strategy

- - Input validation is handled in the UI layer 
- API failures fall back to neutral weather  
- Save/load operations are handled with error checks  
 
---

### Tradeoffs & Future Improvements

If given more time, I would:

- Further separate action processing from `GameEngine`
- Expand event variety and rebalance gameplay
- Improve testability by injecting dependencies (e.g., HTTP client, randomness)  
- Replace the save format with JSON for better extensibility  

---

## AI Usage

AI tools were used as a development aid in the following ways:

- Updating dependencies in `pom.xml`
- Assisting with high-level planning and architectural decisions  
- Helping implement API interaction logic in a simplified and understandable way
- Suggesting a clean approach for clamping resource values to enforce bounds
- Assisting with the builder pattern for `EventChoice` to improve readability and reduce reliance on unclear parameter ordering

All suggestions were reviewed and only incorporated when fully understood.
