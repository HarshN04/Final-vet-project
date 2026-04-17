# Vet Hotel Management System

## Overview

This project is a Java application developed for an Object Oriented Programming project. It manages a veterinary hotel by keeping track of:

- animals
- species
- employees
- vaccines
- assignments between animals/species and employees
- alerts
- date and vaccination status

The system supports both:

- a **Command Line Interface (CLI)**
- a **Graphical User Interface (GUI)**

The application is organized into two main packages:

- `core` → contains the domain logic and business rules
- `ui` → contains the command line and graphical user interfaces

---

## Main Features

### Animal Management
- Add animals
- Remove animals
- Search animals by key
- Search animals by name
- List all animals

Each animal stores:
- unique key
- name
- species
- birth date
- identification
- veterinarian assignment
- next vaccine date
- vaccination status
- dead/alive state

### Species Management
- Add species
- Search species by key
- Search species by name
- Assign caretaker to species
- Remove caretaker from species

Each species stores:
- unique key
- name
- danger level
- list of animals
- caretaker assignment

### Employee Management
- Add employees
- Remove employees
- Search employees by key
- Search employees by name
- List all employees

There are two employee types:
- `Caretaker`
- `Veterinarian`

### Assignment Management
- Assign veterinarians to animals
- Remove veterinarians from animals
- Assign caretakers to species
- Remove caretakers from species

### Satisfaction Calculation
The application calculates employee satisfaction according to the assignment rules.

- **Caretaker satisfaction** depends on the danger level of the species assigned
- **Veterinarian satisfaction** depends on workload based on species population and shared veterinarian responsibility

### Vaccination Management
- Add vaccines
- List vaccines
- Search vaccines
- Mark animals as vaccinated
- Show daily vaccination reminders
- Track missed vaccination consequences

### Alerts
The system can alert users about:
- employees with satisfaction below 14
- animals without a veterinarian
- species without a caretaker
- animals that died today because of missed vaccination

### Date Management
- Current hotel date is tracked
- Date can be advanced manually
- Date can be set manually
- Vaccination reminders and death checks depend on the current date

---

## Project Structure

```text
VetProject/
├── core/
│   ├── Animal.java
│   ├── Species.java
│   ├── Employee.java
│   ├── Caretaker.java
│   ├── Veterinarian.java
│   ├── Vaccine.java
│   └── VetHotel.java
├── ui/
│   ├── CommandLineUI.java
│   └── MainWindow.java
├── AppDataLoader.java
├── Main.java
└── MainCLI.java


File Roles

VetHotel.java
Main backend class that stores and manages all animals, species, employees, vaccines, and date logic.

Animal.java
Represents one animal in the veterinary hotel.

Species.java
Represents one species and its related animals/caretaker.

Employee.java
Abstract base class for employees.

Caretaker.java
Employee subclass for caretakers.

Veterinarian.java
Employee subclass for veterinarians.

Vaccine.java
Represents a vaccine and its target species / administration age.

CommandLineUI.java
Runs the text-based interface.

MainWindow.java
Runs the graphical user interface.

Main.java
Launcher for the GUI version.

MainCLI.java
Launcher for the CLI version.

AppDataLoader.java
Loads sample data used for testing purposes.

Interfaces

1. Command Line Interface (CLI)

The CLI allows users to:

navigate menus
add/search/list animals
add/search/list employees
assign tasks
change vaccination status
show alerts
change the date

This makes it possible to use the full system directly from the terminal.

2. Graphical User Interface (GUI)

The GUI includes:

a Home/Main Menu tab
separate tabs for Animals, Employees, Species, Vaccines, and Alerts & Date
compact action buttons
readable output areas
a dashboard-style home screen

The home screen acts as the GUI version of the required main menu, showing alerts, average employee happiness, navigation options, and date controls.

Default Simulation Date
When the application starts, the simulation date is:
2026-01-01

This is the default starting date used by the system.

Sorting and Search Rules
The project follows these rules:

lists are shown in ascending order of key
comparisons are case-insensitive for keys and names
alphanumeric ordering is lexicographical
names are not necessarily unique
keys are unique

How to Compile and Run
Requirements

Make sure you have:

Java installed
javac and java available in your terminal or command prompt

Compile

Windows Command Prompt
javac Main.java MainCLI.java AppDataLoader.java core\*.java ui\*.java

macOS / Linux / terminals that accept forward slashes
javac Main.java MainCLI.java AppDataLoader.java core/*.java ui/*.java

Run the GUI
java Main

This launches the graphical user interface.

Run the CLI
java MainCLI

This launches the command line interface.

Sample Data

Both launchers load sample data through:

AppDataLoader.seedTestData(hotel);

This means the program starts with example:

species
animals
employees
vaccines
assignments
vaccine due dates

This makes testing easier without having to enter everything manually every time.

Current Implemented Features

The program currently supports:

core object-oriented model
package separation into core and ui
command line interface
graphical user interface
animal management
employee management
species management
vaccine management
search by key and name
sorting by key
assignment and retraction of responsibilities
satisfaction calculations
alerts
manual date change
vaccination reminders
overdue-death tracking
validation and exception handling