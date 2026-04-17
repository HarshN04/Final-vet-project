package core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

/**
 * Main management class for the Vet Hotel system.
 *
 * This class stores and manages:
 * - animals
 * - species
 * - employees
 * - vaccines
 * - the current hotel date
 *
 * It also handles searching, assigning employees, vaccination logic,
 * alerts, removal operations, and sorting.
 */
public class VetHotel {
    private static final LocalDate START_DATE = LocalDate.of(2026, 1, 1);
    private ArrayList<Animal> animals;
    private ArrayList<Species> speciesList;
    private ArrayList<Employee> employees;
    private ArrayList<Vaccine> vaccines;
    private Set<String> uploadedVaccineFileSignatures;
    private LocalDate currentDate;

    /**
     * Creates an empty VetHotel system.
     * The simulation starts on 2026-01-01 as required by the project.
     */
    public VetHotel() {
        animals = new ArrayList<>();
        speciesList = new ArrayList<>();
        employees = new ArrayList<>();
        vaccines = new ArrayList<>();
        uploadedVaccineFileSignatures = new HashSet<>();
        currentDate = START_DATE;
    }

    /**
     * Adds a new animal to the system.
     * Also adds the animal to its species record.
     *
     * @param animal the animal to add
     * @throws IllegalArgumentException if the animal is null
     *         or if another animal already has the same key
     */
    public void addAnimal(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("Animal cannot be null.");
        }
        if (findAnimalByKey(animal.getKey()) != null) {
            throw new IllegalArgumentException("Animal with key '" + animal.getKey() + "' already exists.");
        }
        if (animal.getBirthDate().isAfter(currentDate)) {
            throw new IllegalArgumentException("Animal birth date cannot be after current hotel date (" + currentDate + ").");
        }

        animals.add(animal);
        animal.getSpecies().addAnimal(animal);
        sortAnimalsByKey();
    }

    /**
     * Adds a new species to the system.
     *
     * @param species the species to add
     * @throws IllegalArgumentException if the species is null
     *         or if another species already has the same key
     */
    public void addSpecies(Species species) {
        if (species == null) {
            throw new IllegalArgumentException("Species cannot be null.");
        }
        if (findSpeciesByKey(species.getKey()) != null) {
            throw new IllegalArgumentException("Species with key '" + species.getKey() + "' already exists.");
        }

        speciesList.add(species);
        sortSpeciesByKey();
    }

    /**
     * Adds a new employee to the system.
     *
     * @param employee the employee to add
     * @throws IllegalArgumentException if the employee is null
     *         or if another employee already has the same key
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (findEmployeeByKey(employee.getKey()) != null) {
            throw new IllegalArgumentException("Employee with key '" + employee.getKey() + "' already exists.");
        }

        employees.add(employee);
        sortEmployeesByKey();
    }

    /**
     * Adds a new vaccine to the system.
     *
     * @param vaccine the vaccine to add
     * @throws IllegalArgumentException if the vaccine is null
     *         or if another vaccine already has the same key
     */
    public void addVaccine(Vaccine vaccine) {
        if (vaccine == null) {
            throw new IllegalArgumentException("Vaccine cannot be null.");
        }
        if (findVaccineByKey(vaccine.getKey()) != null) {
            throw new IllegalArgumentException("Vaccine with key '" + vaccine.getKey() + "' already exists.");
        }

        vaccines.add(vaccine);
        sortVaccinesByKey();
    }

    /** @return the full list of animals */
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    /** @return the full list of species */
    public ArrayList<Species> getSpeciesList() {
        return speciesList;
    }

    /** @return the full list of employees */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /** @return the full list of vaccines */
    public ArrayList<Vaccine> getVaccines() {
        return vaccines;
    }

    /** @return the current hotel date */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * Changes the current hotel date.
     *
     * @param currentDate the new date
     * @throws IllegalArgumentException if the date is null
     */
    public void setCurrentDate(LocalDate currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Current date cannot be null.");
        }
        if (currentDate.isBefore(START_DATE)) {
            throw new IllegalArgumentException("Current date cannot be before " + START_DATE + ".");
        }
        this.currentDate = currentDate;
    }

    /**
     * Sets an animal's next vaccine due date.
     * The due date cannot be before the current hotel date.
     *
     * @param animalKey animal key
     * @param dueDate due date to set, or null to clear
     */
    public void setAnimalNextVaccineDate(String animalKey, LocalDate dueDate) {
        Animal animal = findAnimalByKey(animalKey);
        if (animal == null) {
            throw new IllegalArgumentException("Animal with key '" + animalKey + "' was not found.");
        }
        if (dueDate != null && dueDate.isBefore(currentDate)) {
            throw new IllegalArgumentException("Vaccine due date cannot be before current hotel date (" + currentDate + ").");
        }

        animal.setNextVaccineDate(dueDate);
    }

    /**
     * Advances the hotel date by one day.
     * Also resets each animal's "vaccinated today" status.
     */
    public void advanceOneDay() {
        currentDate = currentDate.plusDays(1);

        for (Animal animal : animals) {
            animal.setVaccinatedToday(false);
        }
    }

    /**
     * Finds an animal by its unique key.
     *
     * @param key the animal key
     * @return the matching animal, or null if not found
     */
    public Animal findAnimalByKey(String key) {
        for (Animal animal : animals) {
            if (animal.getKey().equalsIgnoreCase(key)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Finds a species by its unique key.
     *
     * @param key the species key
     * @return the matching species, or null if not found
     */
    public Species findSpeciesByKey(String key) {
        for (Species species : speciesList) {
            if (species.getKey().equalsIgnoreCase(key)) {
                return species;
            }
        }
        return null;
    }

    /**
     * Finds an employee by its unique key.
     *
     * @param key the employee key
     * @return the matching employee, or null if not found
     */
    public Employee findEmployeeByKey(String key) {
        for (Employee employee : employees) {
            if (employee.getKey().equalsIgnoreCase(key)) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Finds a vaccine by its unique key.
     *
     * @param key the vaccine key
     * @return the matching vaccine, or null if not found
     */
    public Vaccine findVaccineByKey(String key) {
        for (Vaccine vaccine : vaccines) {
            if (vaccine.getKey().equalsIgnoreCase(key)) {
                return vaccine;
            }
        }
        return null;
    }

    /**
     * Searches for all animals with a given name.
     * Names are not unique, so this returns a list.
     *
     * @param name the animal name
     * @return list of matching animals
     */
    public ArrayList<Animal> searchAnimalsByName(String name) {
        ArrayList<Animal> results = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                results.add(animal);
            }
        }

        return results;
    }

    /**
     * Searches for all species with a given name.
     *
     * @param name the species name
     * @return list of matching species
     */
    public ArrayList<Species> searchSpeciesByName(String name) {
        ArrayList<Species> results = new ArrayList<>();

        for (Species species : speciesList) {
            if (species.getName().equalsIgnoreCase(name)) {
                results.add(species);
            }
        }

        return results;
    }

    /**
     * Searches for all employees with a given name.
     *
     * @param name the employee name
     * @return list of matching employees
     */
    public ArrayList<Employee> searchEmployeesByName(String name) {
        ArrayList<Employee> results = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.getName().equalsIgnoreCase(name)) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Searches for all vaccines with a given name.
     *
     * @param name the vaccine name
     * @return list of matching vaccines
     */
    public ArrayList<Vaccine> searchVaccinesByName(String name) {
        ArrayList<Vaccine> results = new ArrayList<>();

        for (Vaccine vaccine : vaccines) {
            if (vaccine.getName().equalsIgnoreCase(name)) {
                results.add(vaccine);
            }
        }

        return results;
    }

    /**
     * Assigns a caretaker to a species.
     * If the species already had a caretaker, the old assignment is removed first.
     *
     * @param caretakerKey key of the caretaker
     * @param speciesKey key of the species
     * @return true if assignment succeeded, false otherwise
     */
    public boolean assignCaretakerToSpecies(String caretakerKey, String speciesKey) {
        Employee employee = findEmployeeByKey(caretakerKey);
        Species species = findSpeciesByKey(speciesKey);

        if (employee == null || species == null) {
            return false;
        }

        if (!(employee instanceof Caretaker)) {
            return false;
        }

        Caretaker caretaker = (Caretaker) employee;

        if (species.getCaretaker() != null) {
            species.getCaretaker().removeSpecies(species);
        }

        species.setCaretaker(caretaker);
        caretaker.addSpecies(species);
        return true;
    }

    /**
     * Assigns a veterinarian to an animal.
     * If the animal already had a vet, the old assignment is removed first.
     *
     * @param vetKey key of the veterinarian
     * @param animalKey key of the animal
     * @return true if assignment succeeded, false otherwise
     */
    public boolean assignVeterinarianToAnimal(String vetKey, String animalKey) {
        Employee employee = findEmployeeByKey(vetKey);
        Animal animal = findAnimalByKey(animalKey);

        if (employee == null || animal == null) {
            return false;
        }

        if (!(employee instanceof Veterinarian)) {
            return false;
        }

        Veterinarian veterinarian = (Veterinarian) employee;

        if (animal.getVeterinarian() != null) {
            animal.getVeterinarian().removeAnimal(animal);
        }

        animal.setVeterinarian(veterinarian);
        veterinarian.addAnimal(animal);
        return true;
    }

    /**
     * Removes the veterinarian assignment from an animal.
     *
     * @param animalKey key of the animal
     * @return true if a vet was removed, false otherwise
     */
    public boolean removeVeterinarianFromAnimal(String animalKey) {
        Animal animal = findAnimalByKey(animalKey);

        if (animal == null || animal.getVeterinarian() == null) {
            return false;
        }

        Veterinarian veterinarian = animal.getVeterinarian();
        veterinarian.removeAnimal(animal);
        animal.setVeterinarian(null);
        return true;
    }

    /**
     * Removes the caretaker assignment from a species.
     *
     * @param speciesKey key of the species
     * @return true if a caretaker was removed, false otherwise
     */
    public boolean removeCaretakerFromSpecies(String speciesKey) {
        Species species = findSpeciesByKey(speciesKey);

        if (species == null || species.getCaretaker() == null) {
            return false;
        }

        Caretaker caretaker = species.getCaretaker();
        caretaker.removeSpecies(species);
        species.setCaretaker(null);
        return true;
    }

    /**
     * Removes an animal from the hotel.
     * Also removes the animal from its veterinarian and species records.
     *
     * @param key animal key
     * @return true if removed, false if not found
     */
    public boolean removeAnimalByKey(String key) {
        Animal animal = findAnimalByKey(key);

        if (animal == null) {
            return false;
        }

        if (animal.getVeterinarian() != null) {
            animal.getVeterinarian().removeAnimal(animal);
            animal.setVeterinarian(null);
        }

        animals.remove(animal);
        animal.getSpecies().removeAnimal(animal);
        return true;
    }

    /**
     * Removes a species from the hotel.
     * A species can only be removed if it has no animals.
     *
     * @param key species key
     * @return true if removed, false if not found or still has animals
     */
    public boolean removeSpeciesByKey(String key) {
        Species species = findSpeciesByKey(key);

        if (species == null) {
            return false;
        }

        if (!species.getAnimals().isEmpty()) {
            return false;
        }

        if (species.getCaretaker() != null) {
            species.getCaretaker().removeSpecies(species);
            species.setCaretaker(null);
        }

        speciesList.remove(species);
        return true;
    }

    /**
     * Removes an employee from the hotel.
     * Also clears any caretaker or veterinarian assignments tied to that employee.
     *
     * @param key employee key
     * @return true if removed, false if not found
     */
    public boolean removeEmployeeByKey(String key) {
        Employee employee = findEmployeeByKey(key);

        if (employee == null) {
            return false;
        }

        for (Species species : speciesList) {
            if (species.getCaretaker() != null
                    && species.getCaretaker().getKey().equalsIgnoreCase(key)) {
                species.getCaretaker().removeSpecies(species);
                species.setCaretaker(null);
            }
        }

        for (Animal animal : animals) {
            if (animal.getVeterinarian() != null
                    && animal.getVeterinarian().getKey().equalsIgnoreCase(key)) {
                animal.getVeterinarian().removeAnimal(animal);
                animal.setVeterinarian(null);
            }
        }

        employees.remove(employee);
        return true;
    }

    /**
     * Vaccinates an animal if the vaccine exists, the animal exists,
     * the animal is alive, and the vaccine matches the animal's species.
     *
     * @param animalKey key of the animal
     * @param vaccineKey key of the vaccine
     * @return true if vaccination succeeded, false otherwise
     */
    public boolean vaccinateAnimal(String animalKey, String vaccineKey) {
        Animal animal = findAnimalByKey(animalKey);
        Vaccine vaccine = findVaccineByKey(vaccineKey);

        if (animal == null || vaccine == null) {
            return false;
        }

        if (animal.isDead()) {
            return false;
        }

        if (animal.getSpecies() != vaccine.getTargetSpecies()) {
            return false;
        }

        animal.setVaccinatedToday(true);
        animal.setNextVaccineDate(null);
        return true;
    }

    /**
     * Finds which vaccine is due for a given animal based on:
     * - the animal's species
     * - the animal's age on the current hotel date
     *
     * @param animal the animal
     * @return matching vaccine, or null if none matches
     */
    public Vaccine getDueVaccineForAnimal(Animal animal) {
        int animalAge = animal.getAgeInYears(currentDate);

        for (Vaccine vaccine : vaccines) {
            if (vaccine.getTargetSpecies() == animal.getSpecies()
                    && vaccine.getAdministrationAge() == animalAge) {
                return vaccine;
            }
        }

        return null;
    }

    /**
     * Gets all animals whose next vaccine date is today.
     *
     * @return list of animals due for vaccination today
     */
    public ArrayList<Animal> getAnimalsDueForVaccinationToday() {
        ArrayList<Animal> results = new ArrayList<>();

        for (Animal animal : animals) {
            if (!animal.isDead()
                    && animal.getNextVaccineDate() != null
                    && animal.getNextVaccineDate().equals(currentDate)) {
                results.add(animal);
            }
        }

        return results;
    }

    /**
     * Gets animals that die today because they missed vaccination by 5 days.
     * This method also updates their dead status.
     *
     * @return list of animals that died today
     */
    public ArrayList<Animal> getAnimalsDeadTodayFromMissedVaccination() {
        ArrayList<Animal> results = new ArrayList<>();

        for (Animal animal : animals) {
            if (!animal.isDead()
                    && animal.getNextVaccineDate() != null
                    && animal.getNextVaccineDate().plusDays(5).equals(currentDate)) {
                animal.setDead(true);
                results.add(animal);
            }
        }

        return results;
    }

    /**
     * Convenience method for getting animals dead today.
     *
     * @return list of animals that died today
     */
    public ArrayList<Animal> getAnimalsDeadToday() {
        return getAnimalsDeadTodayFromMissedVaccination();
    }

    /**
     * Gets all employees with satisfaction below 14.
     *
     * @return list of low-satisfaction employees
     */
    public ArrayList<Employee> getLowSatisfactionEmployees() {
        ArrayList<Employee> results = new ArrayList<>();

        for (Employee employee : employees) {
            if (employee.calculateSatisfaction() < 14) {
                results.add(employee);
            }
        }

        return results;
    }

    /**
     * Gets all animals that do not currently have a veterinarian.
     *
     * @return list of animals without a vet
     */
    public ArrayList<Animal> getAnimalsWithoutVeterinarian() {
        ArrayList<Animal> results = new ArrayList<>();

        for (Animal animal : animals) {
            if (animal.getVeterinarian() == null) {
                results.add(animal);
            }
        }

        return results;
    }

    /**
     * Gets all species that do not currently have a caretaker.
     *
     * @return list of species without a caretaker
     */
    public ArrayList<Species> getSpeciesWithoutCaretaker() {
        ArrayList<Species> results = new ArrayList<>();

        for (Species species : speciesList) {
            if (species.getCaretaker() == null) {
                results.add(species);
            }
        }

        return results;
    }

    /**
     * Prints employee satisfaction values to the console.
     * Mostly useful for CLI testing.
     */
    public void printEmployeeSatisfactions() {
        for (Employee employee : employees) {
            System.out.println(employee.getKey() + " - " + employee.getName()
                    + " - Satisfaction: " + employee.calculateSatisfaction());
        }
    }

    /**
     * Prints low-satisfaction employees to the console.
     */
    public void printLowSatisfactionEmployees() {
        ArrayList<Employee> lowEmployees = getLowSatisfactionEmployees();

        System.out.println("\nEmployees with satisfaction below 14:");
        if (lowEmployees.isEmpty()) {
            System.out.println("None");
        } else {
            for (Employee employee : lowEmployees) {
                System.out.println(employee.getKey() + " - " + employee.getName()
                        + " - Satisfaction: " + employee.calculateSatisfaction());
            }
        }
    }

    /**
     * Prints animals without a veterinarian to the console.
     */
    public void printAnimalsWithoutVeterinarian() {
        ArrayList<Animal> animalsWithoutVet = getAnimalsWithoutVeterinarian();

        System.out.println("\nAnimals without veterinarian:");
        if (animalsWithoutVet.isEmpty()) {
            System.out.println("None");
        } else {
            for (Animal animal : animalsWithoutVet) {
                System.out.println(animal.getKey() + " - " + animal.getName());
            }
        }
    }

    /**
     * Prints species without a caretaker to the console.
     */
    public void printSpeciesWithoutCaretaker() {
        ArrayList<Species> speciesWithoutCaretaker = getSpeciesWithoutCaretaker();

        System.out.println("\nSpecies without caretaker:");
        if (speciesWithoutCaretaker.isEmpty()) {
            System.out.println("None");
        } else {
            for (Species species : speciesWithoutCaretaker) {
                System.out.println(species.getKey() + " - " + species.getName());
            }
        }
    }

    /**
     * Prints vaccination reminders to the console.
     */
    public void printVaccinationReminders() {
        System.out.println("\nVaccination reminders for " + currentDate + ":");

        ArrayList<Animal> dueAnimals = getAnimalsDueForVaccinationToday();

        if (dueAnimals.isEmpty()) {
            System.out.println("None");
            return;
        }

        for (Animal animal : dueAnimals) {
            Vaccine vaccine = getDueVaccineForAnimal(animal);

            if (vaccine != null) {
                System.out.println(animal.getKey() + " - " + animal.getName()
                        + " -> " + vaccine.getName());
            } else {
                System.out.println(animal.getKey() + " - " + animal.getName()
                        + " -> No matching vaccine found");
            }
        }
    }

    /**
     * Placeholder method for future vaccine file loading.
     * The real implementation depends on file format sent by lecturer.
     *
     * @param filename vaccine file name
     */
    public String loadVaccinesFromFile(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Vaccine file name cannot be empty.");
        }

        Path filePath = Path.of(filename.trim());
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("File not found: " + filePath.toAbsolutePath());
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file: " + e.getMessage());
        }

        String signature = buildVaccineFileSignature(lines);
        if (uploadedVaccineFileSignatures.contains(signature)) {
            throw new IllegalArgumentException("File already uploaded.");
        }

        int loadedCount = 0;
        ArrayList<String> skippedLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String rawLine = lines.get(i);
            String line = rawLine.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\|");
            if (parts.length != 3) {
                skippedLines.add("Line " + (i + 1) + ": expected 3 fields separated by '|'.");
                continue;
            }

            String vaccineName = parts[0].trim();
            String speciesName = parts[1].trim();
            String administrationAgeText = parts[2].trim();

            if (vaccineName.isEmpty() || speciesName.isEmpty() || administrationAgeText.isEmpty()) {
                skippedLines.add("Line " + (i + 1) + ": one or more fields are empty.");
                continue;
            }

            Species species = findSpeciesByName(speciesName);
            if (species == null) {
                skippedLines.add("Line " + (i + 1) + ": species '" + speciesName + "' was not found.");
                continue;
            }

            int administrationAgeInMonths;
            try {
                administrationAgeInMonths = parseAdministrationAgeInMonths(administrationAgeText);
            } catch (IllegalArgumentException e) {
                skippedLines.add("Line " + (i + 1) + ": " + e.getMessage());
                continue;
            }

            String vaccineKey = generateNextVaccineKey();
            Vaccine vaccine = new Vaccine(vaccineKey, vaccineName, species, administrationAgeInMonths);
            addVaccine(vaccine);
            loadedCount++;
        }

        if (loadedCount == 0) {
            throw new IllegalArgumentException("No vaccines were loaded. " + String.join(" ", skippedLines));
        }

        uploadedVaccineFileSignatures.add(signature);

        if (skippedLines.isEmpty()) {
            return "Loaded " + loadedCount + " vaccines from file.";
        }
        return "Loaded " + loadedCount + " vaccines. Some lines were skipped: " + String.join(" ", skippedLines);
    }

    private String buildVaccineFileSignature(List<String> lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String normalized = line.trim().replaceAll("\\s+", " ");
            if (!normalized.isEmpty()) {
                sb.append(normalized).append('\n');
            }
        }
        return sb.toString();
    }

    private Species findSpeciesByName(String name) {
        for (Species species : speciesList) {
            if (species.getName().equalsIgnoreCase(name)) {
                return species;
            }
        }
        return null;
    }

    private int parseAdministrationAgeInMonths(String ageText) {
        String normalized = ageText.trim().toLowerCase();
        String[] tokens = normalized.split("\\s+");
        if (tokens.length < 1) {
            throw new IllegalArgumentException("administration age is invalid.");
        }

        int value;
        try {
            value = Integer.parseInt(tokens[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("administration age must start with a number.");
        }

        if (value < 0) {
            throw new IllegalArgumentException("administration age cannot be negative.");
        }

        if (tokens.length == 1) {
            return value;
        }

        String unit = tokens[1];
        if (unit.startsWith("month")) {
            return value;
        }
        if (unit.startsWith("year")) {
            return value * 12;
        }

        throw new IllegalArgumentException("unsupported age unit '" + unit + "'. Use months or years.");
    }

    private String generateNextVaccineKey() {
        int maxNumber = 0;
        for (Vaccine vaccine : vaccines) {
            String key = vaccine.getKey().toUpperCase();
            if (key.startsWith("VAC")) {
                String suffix = key.substring(3);
                try {
                    int value = Integer.parseInt(suffix);
                    if (value > maxNumber) {
                        maxNumber = value;
                    }
                } catch (NumberFormatException ignored) {
                    // Ignore non-numeric VAC keys.
                }
            }
        }
        return "VAC" + (maxNumber + 1);
    }

    public void saveStateToFile(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Save file name cannot be empty.");
        }

        ArrayList<String> lines = new ArrayList<>();
        lines.add("CURRENT_DATE\t" + currentDate);

        for (Species species : speciesList) {
            String caretakerKey = species.getCaretaker() == null ? "" : species.getCaretaker().getKey();
            lines.add("SPECIES\t"
                    + escapeField(species.getKey()) + "\t"
                    + escapeField(species.getName()) + "\t"
                    + species.getDangerLevel() + "\t"
                    + escapeField(caretakerKey));
        }

        for (Employee employee : employees) {
            String type = (employee instanceof Caretaker) ? "CARETAKER" : "VETERINARIAN";
            lines.add("EMPLOYEE\t"
                    + type + "\t"
                    + escapeField(employee.getKey()) + "\t"
                    + escapeField(employee.getName()));
        }

        for (Animal animal : animals) {
            String vetKey = animal.getVeterinarian() == null ? "" : animal.getVeterinarian().getKey();
            String nextVaccineDate = animal.getNextVaccineDate() == null ? "" : animal.getNextVaccineDate().toString();
            lines.add("ANIMAL\t"
                    + escapeField(animal.getKey()) + "\t"
                    + escapeField(animal.getName()) + "\t"
                    + escapeField(animal.getSpecies().getKey()) + "\t"
                    + animal.getBirthDate() + "\t"
                    + escapeField(animal.getIdentification()) + "\t"
                    + escapeField(vetKey) + "\t"
                    + escapeField(nextVaccineDate) + "\t"
                    + animal.isVaccinatedToday() + "\t"
                    + animal.isDead());
        }

        for (Vaccine vaccine : vaccines) {
            lines.add("VACCINE\t"
                    + escapeField(vaccine.getKey()) + "\t"
                    + escapeField(vaccine.getName()) + "\t"
                    + escapeField(vaccine.getTargetSpecies().getKey()) + "\t"
                    + vaccine.getAdministrationAge());
        }

        for (String signature : uploadedVaccineFileSignatures) {
            String encoded = Base64.getEncoder().encodeToString(signature.getBytes(StandardCharsets.UTF_8));
            lines.add("UPLOADED_SIGNATURE\t" + encoded);
        }

        try {
            Files.write(Path.of(filename.trim()), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save data: " + e.getMessage());
        }
    }

    public void loadStateFromFile(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Load file name cannot be empty.");
        }

        Path filePath = Path.of(filename.trim());
        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException("Save file not found: " + filePath.toAbsolutePath());
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read save file: " + e.getMessage());
        }

        LocalDate loadedCurrentDate = START_DATE;
        ArrayList<String[]> speciesRows = new ArrayList<>();
        ArrayList<String[]> employeeRows = new ArrayList<>();
        ArrayList<String[]> animalRows = new ArrayList<>();
        ArrayList<String[]> vaccineRows = new ArrayList<>();
        ArrayList<String> signatureRows = new ArrayList<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\t", -1);
            String type = parts[0];

            switch (type) {
                case "CURRENT_DATE":
                    if (parts.length >= 2) {
                        loadedCurrentDate = LocalDate.parse(parts[1]);
                    }
                    break;
                case "SPECIES":
                    speciesRows.add(parts);
                    break;
                case "EMPLOYEE":
                    employeeRows.add(parts);
                    break;
                case "ANIMAL":
                    animalRows.add(parts);
                    break;
                case "VACCINE":
                    vaccineRows.add(parts);
                    break;
                case "UPLOADED_SIGNATURE":
                    if (parts.length >= 2) {
                        signatureRows.add(parts[1]);
                    }
                    break;
                default:
                    break;
            }
        }

        animals.clear();
        speciesList.clear();
        employees.clear();
        vaccines.clear();
        uploadedVaccineFileSignatures.clear();
        currentDate = loadedCurrentDate;

        Map<String, String> speciesCaretakerKeys = new HashMap<>();

        for (String[] row : speciesRows) {
            if (row.length < 5) {
                continue;
            }
            String key = unescapeField(row[1]);
            String name = unescapeField(row[2]);
            double dangerLevel = Double.parseDouble(row[3]);
            String caretakerKey = unescapeField(row[4]);

            addSpecies(new Species(key, name, dangerLevel));
            speciesCaretakerKeys.put(key, caretakerKey);
        }

        for (String[] row : employeeRows) {
            if (row.length < 4) {
                continue;
            }
            String employeeType = row[1];
            String key = unescapeField(row[2]);
            String name = unescapeField(row[3]);

            if ("CARETAKER".equals(employeeType)) {
                addEmployee(new Caretaker(key, name));
            } else if ("VETERINARIAN".equals(employeeType)) {
                addEmployee(new Veterinarian(key, name));
            }
        }

        for (Map.Entry<String, String> entry : speciesCaretakerKeys.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                assignCaretakerToSpecies(entry.getValue(), entry.getKey());
            }
        }

        Map<String, String> animalVetKeys = new HashMap<>();
        for (String[] row : animalRows) {
            if (row.length < 10) {
                continue;
            }
            String key = unescapeField(row[1]);
            String name = unescapeField(row[2]);
            String speciesKey = unescapeField(row[3]);
            LocalDate birthDate = LocalDate.parse(row[4]);
            String identification = unescapeField(row[5]);
            String vetKey = unescapeField(row[6]);
            String nextVaccineDateText = unescapeField(row[7]);
            boolean vaccinatedToday = Boolean.parseBoolean(row[8]);
            boolean dead = Boolean.parseBoolean(row[9]);

            Species species = findSpeciesByKey(speciesKey);
            if (species == null) {
                continue;
            }

            Animal animal = new Animal(key, name, species, birthDate, identification);
            addAnimal(animal);

            if (!nextVaccineDateText.isEmpty()) {
                animal.setNextVaccineDate(LocalDate.parse(nextVaccineDateText));
            }
            animal.setVaccinatedToday(vaccinatedToday);
            animal.setDead(dead);

            animalVetKeys.put(key, vetKey);
        }

        for (Map.Entry<String, String> entry : animalVetKeys.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                assignVeterinarianToAnimal(entry.getValue(), entry.getKey());
            }
        }

        for (String[] row : vaccineRows) {
            if (row.length < 5) {
                continue;
            }
            String key = unescapeField(row[1]);
            String name = unescapeField(row[2]);
            String speciesKey = unescapeField(row[3]);
            int administrationAge = Integer.parseInt(row[4]);

            Species species = findSpeciesByKey(speciesKey);
            if (species == null) {
                continue;
            }
            addVaccine(new Vaccine(key, name, species, administrationAge));
        }

        for (String encoded : signatureRows) {
            try {
                String signature = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
                uploadedVaccineFileSignatures.add(signature);
            } catch (IllegalArgumentException ignored) {
                // Skip malformed signature lines.
            }
        }
    }

    private String escapeField(String value) {
        return value.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private String unescapeField(String value) {
        StringBuilder sb = new StringBuilder();
        boolean escaping = false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!escaping) {
                if (c == '\\') {
                    escaping = true;
                } else {
                    sb.append(c);
                }
                continue;
            }

            switch (c) {
                case 't':
                    sb.append('\t');
                    break;
                case 'n':
                    sb.append('\n');
                    break;
                case 'r':
                    sb.append('\r');
                    break;
                case '\\':
                    sb.append('\\');
                    break;
                default:
                    sb.append(c);
                    break;
            }
            escaping = false;
        }
        if (escaping) {
            sb.append('\\');
        }
        return sb.toString();
    }

    /** Sorts animals by key in case-insensitive order. */
    private void sortAnimalsByKey() {
        Collections.sort(animals, Comparator.comparing(animal -> animal.getKey().toLowerCase()));
    }

    /** Sorts species by key in case-insensitive order. */
    private void sortSpeciesByKey() {
        Collections.sort(speciesList, Comparator.comparing(species -> species.getKey().toLowerCase()));
    }

    /** Sorts employees by key in case-insensitive order. */
    private void sortEmployeesByKey() {
        Collections.sort(employees, Comparator.comparing(employee -> employee.getKey().toLowerCase()));
    }

    /** Sorts vaccines by key in case-insensitive order. */
    private void sortVaccinesByKey() {
        Collections.sort(vaccines, Comparator.comparing(vaccine -> vaccine.getKey().toLowerCase()));
    }
}