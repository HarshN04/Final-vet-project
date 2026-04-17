package ui;

import core.Animal;
import core.Caretaker;
import core.Employee;
import core.Species;
import core.VetHotel;
import core.Veterinarian;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineUI {
    private final Scanner scanner = new Scanner(System.in);
    private final VetHotel hotel;

    public CommandLineUI(VetHotel hotel) {
        this.hotel = hotel;
    }

    public void start() {
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("VET HOTEL MAIN MENU");
            System.out.println("Current date: " + hotel.getCurrentDate());
            System.out.println("Average employee happiness: " + calculateAverageEmployeeHappiness());
            System.out.println("1. Show alerts");
            System.out.println("2. Animal menu");
            System.out.println("3. Employee menu");
            System.out.println("4. Change date");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAlerts();
                    break;
                case "2":
                    animalMenu();
                    break;
                case "3":
                    employeeMenu();
                    break;
                case "4":
                    changeDateMenu();
                    break;
                case "0":
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void animalMenu() {
        while (true) {
            System.out.println("\n--- ANIMAL MENU ---");
            System.out.println("1. Add animal");
            System.out.println("2. List all animals");
            System.out.println("3. Search animal by key");
            System.out.println("4. Search animal by name");
            System.out.println("5. Change vaccination status");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addAnimal();
                    break;
                case "2":
                    listAnimals();
                    break;
                case "3":
                    searchAnimalByKey();
                    break;
                case "4":
                    searchAnimalByName();
                    break;
                case "5":
                    changeVaccinationStatus();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void employeeMenu() {
        while (true) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Add employee");
            System.out.println("2. List all employees");
            System.out.println("3. Search employee by key");
            System.out.println("4. Search employee by name");
            System.out.println("5. Assign veterinarian to animal");
            System.out.println("6. Assign caretaker to species");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    listEmployees();
                    break;
                case "3":
                    searchEmployeeByKey();
                    break;
                case "4":
                    searchEmployeeByName();
                    break;
                case "5":
                    assignVeterinarianToAnimal();
                    break;
                case "6":
                    assignCaretakerToSpecies();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void addAnimal() {
        try {
            System.out.print("Animal key: ");
            String key = scanner.nextLine();

            System.out.print("Animal name: ");
            String name = scanner.nextLine();

            System.out.print("Species key: ");
            String speciesKey = scanner.nextLine();

            Species species = hotel.findSpeciesByKey(speciesKey);
            if (species == null) {
                System.out.println("Species not found.");
                return;
            }

            System.out.print("Birth date (YYYY-MM-DD): ");
            LocalDate birthDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Identification: ");
            String identification = scanner.nextLine();

            Animal animal = new Animal(key, name, species, birthDate, identification);
            hotel.addAnimal(animal);

            System.out.println("Animal added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAnimals() {
        ArrayList<Animal> animals = hotel.getAnimals();

        if (animals.isEmpty()) {
            System.out.println("No animals found.");
            return;
        }

        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    private void searchAnimalByKey() {
        System.out.print("Enter animal key: ");
        String key = scanner.nextLine();

        Animal animal = hotel.findAnimalByKey(key);
        if (animal == null) {
            System.out.println("Animal not found.");
        } else {
            System.out.println(animal);
        }
    }

    private void searchAnimalByName() {
        System.out.print("Enter animal name: ");
        String name = scanner.nextLine();

        ArrayList<Animal> results = hotel.searchAnimalsByName(name);
        if (results.isEmpty()) {
            System.out.println("No animals found.");
        } else {
            for (Animal animal : results) {
                System.out.println(animal);
            }
        }
    }

    private void changeVaccinationStatus() {
        try {
            System.out.print("Animal key: ");
            String animalKey = scanner.nextLine();

            System.out.print("Vaccine key: ");
            String vaccineKey = scanner.nextLine();

            boolean success = hotel.vaccinateAnimal(animalKey, vaccineKey);
            if (success) {
                System.out.println("Vaccination updated successfully.");
            } else {
                System.out.println("Vaccination failed.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addEmployee() {
        try {
            System.out.print("Employee type (caretaker/veterinarian): ");
            String type = scanner.nextLine();

            System.out.print("Employee key: ");
            String key = scanner.nextLine();

            System.out.print("Employee name: ");
            String name = scanner.nextLine();

            String normalizedType = type.trim().toLowerCase();
            boolean isCaretaker = normalizedType.equals("caretaker") || normalizedType.equals("care");
            boolean isVet = normalizedType.equals("veterinarian") || normalizedType.equals("vet");

            if (isCaretaker && !key.matches("C\\d+")) {
                System.out.println("Caretaker keys must follow C1, C2, ...");
                return;
            }
            if (isVet && !key.matches("V\\d+")) {
                System.out.println("Veterinarian keys must follow V1, V2, ...");
                return;
            }

            if (isCaretaker) {
                hotel.addEmployee(new Caretaker(key, name));
                System.out.println("Caretaker added successfully.");
            } else if (isVet) {
                hotel.addEmployee(new Veterinarian(key, name));
                System.out.println("Veterinarian added successfully.");
            } else {
                System.out.println("Invalid employee type. Use caretaker/care or veterinarian/vet.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listEmployees() {
        ArrayList<Employee> employees = hotel.getEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee employee : employees) {
            System.out.println(employee.getKey() + " - " + employee.getName()
                    + " - Satisfaction: " + employee.calculateSatisfaction());
        }
    }

    private void searchEmployeeByKey() {
        System.out.print("Enter employee key: ");
        String key = scanner.nextLine();

        Employee employee = hotel.findEmployeeByKey(key);
        if (employee == null) {
            System.out.println("Employee not found.");
        } else {
            System.out.println(employee.getKey() + " - " + employee.getName()
                    + " - Satisfaction: " + employee.calculateSatisfaction());
        }
    }

    private void searchEmployeeByName() {
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        ArrayList<Employee> results = hotel.searchEmployeesByName(name);
        if (results.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : results) {
                System.out.println(employee.getKey() + " - " + employee.getName()
                        + " - Satisfaction: " + employee.calculateSatisfaction());
            }
        }
    }

    private void assignVeterinarianToAnimal() {
        System.out.print("Veterinarian key: ");
        String vetKey = scanner.nextLine();

        System.out.print("Animal key: ");
        String animalKey = scanner.nextLine();

        boolean success = hotel.assignVeterinarianToAnimal(vetKey, animalKey);
        if (success) {
            System.out.println("Veterinarian assigned successfully.");
        } else {
            System.out.println("Assignment failed.");
        }
    }

    private void assignCaretakerToSpecies() {
        System.out.print("Caretaker key: ");
        String caretakerKey = scanner.nextLine();

        System.out.print("Species key: ");
        String speciesKey = scanner.nextLine();

        boolean success = hotel.assignCaretakerToSpecies(caretakerKey, speciesKey);
        if (success) {
            System.out.println("Caretaker assigned successfully.");
        } else {
            System.out.println("Assignment failed.");
        }
    }

    private void changeDateMenu() {
        try {
            System.out.println("1. Advance one day");
            System.out.println("2. Set specific date");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    hotel.advanceOneDay();
                    System.out.println("Date advanced to: " + hotel.getCurrentDate());
                    break;
                case "2":
                    System.out.print("Enter new date (YYYY-MM-DD): ");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    hotel.setCurrentDate(newDate);
                    System.out.println("Date changed to: " + hotel.getCurrentDate());
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showAlerts() {
        System.out.println("\n--- ALERTS ---");

        System.out.println("Low satisfaction employees:");
        ArrayList<Employee> lowEmployees = hotel.getLowSatisfactionEmployees();
        if (lowEmployees.isEmpty()) {
            System.out.println("None");
        } else {
            for (Employee employee : lowEmployees) {
                System.out.println(employee.getKey() + " - " + employee.getName());
            }
        }

        System.out.println("\nAnimals without veterinarian:");
        ArrayList<Animal> animalsWithoutVet = hotel.getAnimalsWithoutVeterinarian();
        if (animalsWithoutVet.isEmpty()) {
            System.out.println("None");
        } else {
            for (Animal animal : animalsWithoutVet) {
                System.out.println(animal.getKey() + " - " + animal.getName());
            }
        }

        System.out.println("\nSpecies without caretaker:");
        ArrayList<Species> speciesWithoutCaretaker = hotel.getSpeciesWithoutCaretaker();
        if (speciesWithoutCaretaker.isEmpty()) {
            System.out.println("None");
        } else {
            for (Species species : speciesWithoutCaretaker) {
                System.out.println(species.getKey() + " - " + species.getName());
            }
        }

        System.out.println("\nVaccination reminders:");
        hotel.printVaccinationReminders();
    }

    private double calculateAverageEmployeeHappiness() {
        ArrayList<Employee> employees = hotel.getEmployees();

        if (employees.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Employee employee : employees) {
            total += employee.calculateSatisfaction();
        }

        return total / employees.size();
    }
}