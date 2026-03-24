# 🅿️ Low-Level Design (LLD): The Parking Lot System

## 1. Requirements Gathering (Scoping the Problem)
*Never assume the requirements. Always clarify the scope with your interviewer to show product-minded thinking.*

### Key Questions to Ask:
* **Capacity & Layout:** Is it a single-level or multi-level parking lot?
* **Vehicle Types:** What types of vehicles do we support? (e.g., Motorcycles, Cars, Trucks, EVs).
* **Spot Types:** Are there specific spot types? (e.g., Compact, Large, Handicapped, EV charging).
* **Pricing Model:** How is parking billed? (Hourly, flat rate, vehicle-dependent?)
* **Entry/Exit:** Are there multiple entry and exit panels? (This introduces concurrency issues).
* **Monitoring:** Does the system need to display "Lot Full" or available spot counts on a display board?

---

## 2. Identify Actors and Use Cases
*Who interacts with the system and what do they do?*

* **Actor 1: Customer**
    * Takes a ticket at the entry gate.
    * Parks their vehicle in an assigned/available spot.
    * Pays the ticket at the exit gate or an automated kiosk.
* **Actor 2: System / Parking Lot Attendant**
    * Checks if the lot is full.
    * Assigns the nearest available spot to a vehicle.
    * Calculates the fee upon exit.
    * Frees the spot after the vehicle leaves.

---

## 3. Core Entities & Class Identification (Nouns)
*Translate the nouns from the requirements into Object-Oriented classes.*

* `ParkingLot`: The central system (Likely a **Singleton**).
* `Level`: Represents a floor, contains multiple parking spots.
* `ParkingSpot`: Tracks its availability, spot type, and the vehicle currently parked.
* `Vehicle` (Abstract/Interface): Base class for `Car`, `Truck`, `Motorcycle`.
* `Ticket`: Tracks entry time, vehicle info, and spot assignment.
* `Gate` (Abstract): Base class for `EntryGate` and `ExitGate`.
* `PaymentSystem`: Handles the calculation and processing of fees.

---

## 4. Applying Design Patterns
*How do we structure this to adhere to SOLID principles and make it extensible?*

* **Singleton Pattern:** Ensure only one instance of the `ParkingLot` exists in memory to maintain a single source of truth for available spots.
* **Factory Pattern:** Use a `VehicleFactory` to instantiate the correct vehicle type based on sensor input, or a `TicketFactory` to generate tickets.
* **Strategy Pattern (Crucial):** How do you find an empty spot?
    * Create a `ParkingStrategy` interface.
    * Implement strategies like `NearestToGateStrategy` or `FillLowestLevelFirstStrategy`.
    * This satisfies the Open/Closed Principle—you can add new parking algorithms later without changing the core lot logic.
* **State Pattern:** A `ParkingSpot` can have states: `AVAILABLE`, `OCCUPIED`, `OUT_OF_SERVICE`.

---

## 5. Identifying the Hard Technical Challenges (SDE-2 Focus)

### A. Concurrency (The Multiple Gate Problem)
If two cars pull up to Entry Gate 1 and Entry Gate 2 at the exact same millisecond, and there is only ONE spot left, how do you prevent them from being assigned the same spot?
* **Solution:** You must use thread synchronization. In Java, this means using `ReentrantLock` or `synchronized` blocks around the `assignSpot()` method to ensure atomic operations.

### B. Extensibility (The "What If" Game)
What if we add Electric Vehicles that need charging spots?
* **Solution:** Because we use an abstract `Vehicle` class and a `ParkingSpot` class with a `SpotType` enum, we just add `EV` to the vehicle types and `EV_CHARGING` to spot types without rewriting the whole system.

### C. Search Optimization
How quickly can we find an empty spot?
* **O(N) Approach:** Iterating through every spot on every level. (Too slow).
* **O(1) Approach:** Maintain a `Map<SpotType, Queue<ParkingSpot>>` where the queue always has the next available spot at the front.