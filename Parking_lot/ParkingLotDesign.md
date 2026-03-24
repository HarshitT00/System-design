# 🅿️ Parking Lot Low-Level Design (LLD)

## Overview
This project is a production-ready Low-Level Design implementation of a multi-level Parking Lot system. It is designed to demonstrate advanced Object-Oriented Programming (OOP) concepts, standard Design Patterns, and thread-safe concurrency interviews.

---

## 📂 Project Structure (Separation of Concerns)
The code is strictly modular to prevent tight coupling and allow independent scaling of features.

* `com.parkinglot.enums`: Contains constants (`VehicleType`, `SpotType`).
* `com.parkinglot.models`: Domain entities representing physical or data objects (`Vehicle`, `ParkingSpot`, `Level`, `Ticket`).
* `com.parkinglot.strategy`: Isolated algorithmic logic (`ParkingStrategy`, `PaymentStrategy`).
* `com.parkinglot.core`: The central system controller (`ParkingLot`).
* `com.parkinglot.*Runner`: Executable files to simulate flows and stress-test the system.

---

## 🧠 Core Concepts & SOLID Principles Applied

### 1. Single Responsibility Principle (SRP)
* **Implementation:** A `ParkingSpot` only tracks its own state (occupied/free). A `Level` only tracks its collection of spots. The `HourlyPaymentStrategy` only cares about calculating math.
* **Why:** If the pricing math changes, we don't accidentally break the physical spot assignment logic.

### 2. Open/Closed Principle (OCP)
* **Implementation:** The `Vehicle` class is `abstract`.
* **Why:** If the business wants to support Electric Vehicles (EVs) tomorrow, we just create `class EV extends Vehicle`. We *extend* the system without *modifying* the existing `Vehicle` or `ParkingLot` core logic.

### 3. Dependency Inversion Principle (DIP)
* **Implementation:** The `ParkingLot` class does not depend on a hardcoded pricing algorithm or search algorithm. It depends on `PaymentStrategy` and `ParkingStrategy` interfaces.
* **Why:** Algorithms can be injected at runtime. We can swap a `NearestSpotStrategy` for a `FillLowestLevelFirstStrategy` without changing the `ParkingLot` class.

---

## 🏗️ Design Patterns

### 1. The Singleton Pattern (`ParkingLot.java`)
* **Use Case:** A physical building only has one central parking management system. We must prevent the creation of multiple `ParkingLot` instances in memory, which would lead to double-booking spots.
* **Implementation:** Private constructor, static instance, and Double-Checked Locking.

### 2. The Strategy Pattern (`strategy/`)
* **Use Case:** Decoupling volatile business rules (like how to find a spot or how to charge money) from the core infrastructure.
* **Implementation:** Interfaces define the contract (`calculateFee()`), and concrete classes implement the specific rules (`HourlyPaymentStrategy`).

---

## ⚡ Concurrency & Thread Safety
In a real-world scenario, multiple entry gates (threads) will attempt to assign the last available spot at the exact same millisecond.

* **`volatile` Keyword:** Applied to the Singleton `instance` variable. It forces threads to read the variable directly from main memory (RAM) rather than relying on stale CPU caches, preventing the accidental creation of duplicate Lot instances.
* **`synchronized` Keyword:** Applied to the `parkVehicle` and `processExit` methods. It acts as an intrinsic lock (mutex). If 10 cars arrive at exactly the same time, the JVM forces them to queue up and execute the assignment logic one by one, completely eliminating Race Conditions.

---

## ▶️ How to Run

### 1. Standard Flow Simulation
Run `ParkingLotRunner.java`.
* **What it does:** Simulates a sequence of vehicles entering the lot, handles lot capacity rejections, simulates time passing, and processes exits to calculate hourly fees based on vehicle type.

### 2. Concurrency Stress Test
Run `ConcurrencyRunner.java`.
* **What it does:** Uses Java's `ExecutorService` and a `CountDownLatch` to fire 10 simultaneous thread requests at a Parking Lot with only 2 available spots.
* **What to watch for:** Proves that the `synchronized` lock successfully prevents double-booking, ensuring exactly 2 threads succeed and 8 fail safely.