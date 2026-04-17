package ui;

import core.Animal;
import core.Caretaker;
import core.Employee;
import core.Species;
import core.Vaccine;
import core.VetHotel;
import core.Veterinarian;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Graphical User Interface for the Vet Hotel system.
 *
 * This class provides:
 * - a Home/Main Menu tab
 * - Animals tab
 * - Employees tab
 * - Species tab
 * - Vaccines tab
 * - Alerts & Date tab
 *
 * Each non-home tab has its own output area.
 * The Home tab acts as the GUI version of the main menu/dashboard.
 */
public class MainWindow extends JFrame {
    private final VetHotel hotel;

    // Output areas for each tab
    private JTextArea homeInfoArea;
    private JTextArea animalsOutputArea;
    private JTextArea employeesOutputArea;
    private JTextArea speciesOutputArea;
    private JTextArea vaccinesOutputArea;
    private JTextArea alertsDateOutputArea;

    /**
     * Creates the main application window and builds all tabs.
     *
     * @param hotel the backend system used by the GUI
     */
    public MainWindow(VetHotel hotel) {
        this.hotel = hotel;

        setTitle("Vet Hotel Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main tabbed navigation for the GUI
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Home", createHomeTab(tabbedPane));
        tabbedPane.addTab("Animals", createAnimalsTab());
        tabbedPane.addTab("Employees", createEmployeesTab());
        tabbedPane.addTab("Species", createSpeciesTab());
        tabbedPane.addTab("Vaccines", createVaccinesTab());
        tabbedPane.addTab("Alerts & Date", createAlertsDateTab());

        add(tabbedPane);
    }

    /**
     * Builds the Home tab.
     * This tab works as the main menu/dashboard of the GUI.
     *
     * @param tabbedPane the main tabbed pane, used for navigation buttons
     * @return the completed Home tab panel
     */
    private JPanel createHomeTab(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Vet Hotel Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        homeInfoArea = createOutputArea(15);
        homeInfoArea.setText(buildHomeSummary());

        JButton refreshButton = new JButton("Refresh Home");
        JButton animalsButton = new JButton("Go to Animals");
        JButton employeesButton = new JButton("Go to Employees");
        JButton speciesButton = new JButton("Go to Species");
        JButton vaccinesButton = new JButton("Go to Vaccines");
        JButton alertsButton = new JButton("Go to Alerts & Date");

        // Home tab navigation buttons jump to the related tabs
        refreshButton.addActionListener(e -> refreshHomeTab());
        animalsButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        employeesButton.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        speciesButton.addActionListener(e -> tabbedPane.setSelectedIndex(3));
        vaccinesButton.addActionListener(e -> tabbedPane.setSelectedIndex(4));
        alertsButton.addActionListener(e -> tabbedPane.setSelectedIndex(5));

        JPanel navPanel = new JPanel(new GridLayout(2, 3, 12, 12));
        navPanel.add(refreshButton);
        navPanel.add(animalsButton);
        navPanel.add(employeesButton);
        navPanel.add(speciesButton);
        navPanel.add(vaccinesButton);
        navPanel.add(alertsButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(homeInfoArea), BorderLayout.CENTER);
        panel.add(navPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Builds the Animals tab.
     *
     * @return panel containing animal actions and output area
     */
    private JPanel createAnimalsTab() {
        JPanel panel = createStandardTabPanel();

        JPanel buttonPanel = createButtonRowPanel();
        JButton listAnimalsButton = new JButton("List Animals");
        JButton addAnimalButton = new JButton("Add Animal");
        JButton searchAnimalByKeyButton = new JButton("Search by Key");
        JButton searchAnimalByNameButton = new JButton("Search by Name");
        JButton assignVetButton = new JButton("Assign Vet");
        JButton removeVetButton = new JButton("Remove Vet");
        JButton vaccinateAnimalButton = new JButton("Vaccinate");
        JButton removeAnimalButton = new JButton("Remove Animal");

        listAnimalsButton.addActionListener(e -> listAnimals());
        addAnimalButton.addActionListener(e -> addAnimal());
        searchAnimalByKeyButton.addActionListener(e -> searchAnimalByKey());
        searchAnimalByNameButton.addActionListener(e -> searchAnimalByName());
        assignVetButton.addActionListener(e -> assignVeterinarianToAnimal());
        removeVetButton.addActionListener(e -> removeVeterinarianFromAnimal());
        vaccinateAnimalButton.addActionListener(e -> vaccinateAnimal());
        removeAnimalButton.addActionListener(e -> removeAnimal());

        buttonPanel.add(listAnimalsButton);
        buttonPanel.add(addAnimalButton);
        buttonPanel.add(searchAnimalByKeyButton);
        buttonPanel.add(searchAnimalByNameButton);
        buttonPanel.add(assignVetButton);
        buttonPanel.add(removeVetButton);
        buttonPanel.add(vaccinateAnimalButton);
        buttonPanel.add(removeAnimalButton);

        animalsOutputArea = createOutputArea(14);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(animalsOutputArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Builds the Employees tab.
     *
     * @return panel containing employee actions and output area
     */
    private JPanel createEmployeesTab() {
        JPanel panel = createStandardTabPanel();

        JPanel buttonPanel = createButtonRowPanel();
        JButton listVeterinariansButton = new JButton("List Veterinarian");
        JButton listCaretakersButton = new JButton("List Caretaker");
        JButton addEmployeeButton = new JButton("Add Employee");
        JButton searchEmployeeByKeyButton = new JButton("Search by Key");
        JButton searchEmployeeByNameButton = new JButton("Search by Name");
        JButton removeEmployeeButton = new JButton("Remove Employee");
        JButton showAverageButton = new JButton("Average Happiness");

        listVeterinariansButton.addActionListener(e -> listVeterinarians());
        listCaretakersButton.addActionListener(e -> listCaretakers());
        addEmployeeButton.addActionListener(e -> addEmployee());
        searchEmployeeByKeyButton.addActionListener(e -> searchEmployeeByKey());
        searchEmployeeByNameButton.addActionListener(e -> searchEmployeeByName());
        removeEmployeeButton.addActionListener(e -> removeEmployee());
        showAverageButton.addActionListener(e -> showAverageHappiness());

        buttonPanel.add(listVeterinariansButton);
        buttonPanel.add(listCaretakersButton);
        buttonPanel.add(addEmployeeButton);
        buttonPanel.add(searchEmployeeByKeyButton);
        buttonPanel.add(searchEmployeeByNameButton);
        buttonPanel.add(removeEmployeeButton);
        buttonPanel.add(showAverageButton);

        employeesOutputArea = createOutputArea(14);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(employeesOutputArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Builds the Species tab.
     *
     * @return panel containing species actions and output area
     */
    private JPanel createSpeciesTab() {
        JPanel panel = createStandardTabPanel();

        JPanel buttonPanel = createButtonRowPanel();
        JButton listSpeciesButton = new JButton("List Species");
        JButton addSpeciesButton = new JButton("Add Species");
        JButton searchSpeciesByKeyButton = new JButton("Search by Key");
        JButton searchSpeciesByNameButton = new JButton("Search by Name");
        JButton assignCaretakerButton = new JButton("Assign Caretaker");
        JButton removeCaretakerButton = new JButton("Remove Caretaker");
        JButton removeSpeciesButton = new JButton("Remove Species");
        JButton showSpeciesWithoutCaretakerButton = new JButton("Species Without Caretaker");

        listSpeciesButton.addActionListener(e -> listSpecies());
        addSpeciesButton.addActionListener(e -> addSpecies());
        searchSpeciesByKeyButton.addActionListener(e -> searchSpeciesByKey());
        searchSpeciesByNameButton.addActionListener(e -> searchSpeciesByName());
        assignCaretakerButton.addActionListener(e -> assignCaretakerToSpecies());
        removeCaretakerButton.addActionListener(e -> removeCaretakerFromSpecies());
        removeSpeciesButton.addActionListener(e -> removeSpecies());
        showSpeciesWithoutCaretakerButton.addActionListener(e -> showSpeciesWithoutCaretaker());

        buttonPanel.add(listSpeciesButton);
        buttonPanel.add(addSpeciesButton);
        buttonPanel.add(searchSpeciesByKeyButton);
        buttonPanel.add(searchSpeciesByNameButton);
        buttonPanel.add(assignCaretakerButton);
        buttonPanel.add(removeCaretakerButton);
        buttonPanel.add(removeSpeciesButton);
        buttonPanel.add(showSpeciesWithoutCaretakerButton);

        speciesOutputArea = createOutputArea(14);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(speciesOutputArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Builds the Vaccines tab.
     *
     * @return panel containing vaccine actions and output area
     */
    private JPanel createVaccinesTab() {
        JPanel panel = createStandardTabPanel();

        JPanel buttonPanel = createButtonRowPanel();
        JButton addVaccineButton = new JButton("Add Vaccine");
        JButton listVaccinesButton = new JButton("List Vaccines");
        JButton showRemindersButton = new JButton("Show Reminders");
        JButton loadVaccinesFileButton = new JButton("Load Vaccine File");

        addVaccineButton.addActionListener(e -> addVaccine());
        listVaccinesButton.addActionListener(e -> listVaccines());
        showRemindersButton.addActionListener(e -> showVaccinationReminders());
        loadVaccinesFileButton.addActionListener(e -> loadVaccinesFile());

        buttonPanel.add(addVaccineButton);
        buttonPanel.add(listVaccinesButton);
        buttonPanel.add(showRemindersButton);
        buttonPanel.add(loadVaccinesFileButton);

        vaccinesOutputArea = createOutputArea(14);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(vaccinesOutputArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Builds the Alerts & Date tab.
     *
     * @return panel containing alert/date actions and output area
     */
    private JPanel createAlertsDateTab() {
        JPanel panel = createStandardTabPanel();

        JPanel buttonPanel = createButtonRowPanel();
        JButton showAlertsButton = new JButton("Show Alerts");
        JButton showDateButton = new JButton("Show Date");
        JButton advanceDateButton = new JButton("Advance One Day");
        JButton setDateButton = new JButton("Set Specific Date");
        JButton saveDataButton = new JButton("Save Data");
        JButton showDeadTodayButton = new JButton("Show Animals Dead Today");

        showAlertsButton.addActionListener(e -> showAlerts());
        showDateButton.addActionListener(e -> showCurrentDate());
        advanceDateButton.addActionListener(e -> advanceOneDay());
        setDateButton.addActionListener(e -> setSpecificDate());
        saveDataButton.addActionListener(e -> saveData());
        showDeadTodayButton.addActionListener(e -> showAnimalsDeadToday());

        buttonPanel.add(showAlertsButton);
        buttonPanel.add(showDateButton);
        buttonPanel.add(advanceDateButton);
        buttonPanel.add(setDateButton);
        buttonPanel.add(saveDataButton);
        buttonPanel.add(showDeadTodayButton);

        alertsDateOutputArea = createOutputArea(14);

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(alertsDateOutputArea), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a reusable output area with standard formatting.
     *
     * @param fontSize output font size
     * @return configured JTextArea
     */
    private JTextArea createOutputArea(int fontSize) {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setLineWrap(false);
        area.setWrapStyleWord(false);
        return area;
    }

    /**
     * Creates a standard tab panel layout.
     *
     * @return a BorderLayout panel with margins
     */
    private JPanel createStandardTabPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    /**
     * Creates a compact button row panel for tab actions.
     *
     * @return a FlowLayout panel for buttons
     */
    private JPanel createButtonRowPanel() {
        return new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
    }

    /**
     * Refreshes the Home tab summary after data changes.
     */
    private void refreshHomeTab() {
        if (homeInfoArea != null) {
            homeInfoArea.setText(buildHomeSummary());
        }
    }

    /**
     * Builds the text shown on the Home dashboard.
     *
     * @return formatted Home summary text
     */
    private String buildHomeSummary() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== HOME / MAIN MENU ===\n\n");
        sb.append("Current Date: ").append(hotel.getCurrentDate()).append("\n");
        sb.append("Average Employee Happiness: ").append(calculateAverageHappiness()).append("\n\n");

        sb.append("Alerts Summary\n");
        sb.append(separator());

        ArrayList<Employee> lowEmployees = hotel.getLowSatisfactionEmployees();
        ArrayList<Animal> animalsWithoutVet = hotel.getAnimalsWithoutVeterinarian();
        ArrayList<Species> speciesWithoutCaretaker = hotel.getSpeciesWithoutCaretaker();
        ArrayList<Animal> dueAnimals = hotel.getAnimalsDueForVaccinationToday();

        sb.append("Low Satisfaction Employees: ").append(lowEmployees.size()).append("\n");
        sb.append("Animals Without Veterinarian: ").append(animalsWithoutVet.size()).append("\n");
        sb.append("Species Without Caretaker: ").append(speciesWithoutCaretaker.size()).append("\n");
        sb.append("Vaccines Due Today: ").append(dueAnimals.size()).append("\n\n");

        if (!lowEmployees.isEmpty()) {
            sb.append("Employees Below Satisfaction Threshold:\n");
            for (Employee employee : lowEmployees) {
                sb.append("- ")
                        .append(employee.getKey())
                        .append(" - ")
                        .append(employee.getName())
                        .append(" (")
                        .append(employee.calculateSatisfaction())
                        .append(")\n");
            }
            sb.append("\n");
        }

        if (!animalsWithoutVet.isEmpty()) {
            sb.append("Animals Missing Veterinarian:\n");
            for (Animal animal : animalsWithoutVet) {
                sb.append("- ")
                        .append(animal.getKey())
                        .append(" - ")
                        .append(animal.getName())
                        .append("\n");
            }
            sb.append("\n");
        }

        if (!speciesWithoutCaretaker.isEmpty()) {
            sb.append("Species Missing Caretaker:\n");
            for (Species species : speciesWithoutCaretaker) {
                sb.append("- ")
                        .append(species.getKey())
                        .append(" - ")
                        .append(species.getName())
                        .append("\n");
            }
            sb.append("\n");
        }

        if (lowEmployees.isEmpty() && animalsWithoutVet.isEmpty() && speciesWithoutCaretaker.isEmpty()) {
            sb.append("No active alerts.\n");
        }

        return sb.toString();
    }

    /**
     * Calculates the average employee happiness/satisfaction.
     *
     * @return average satisfaction, or 0.0 if there are no employees
     */
    private double calculateAverageHappiness() {
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

    /** @return a standard separator line for text output */
    private String separator() {
        return "----------------------------------------\n";
    }

    /**
     * Formats an animal for GUI display.
     *
     * @param animal the animal to format
     * @return readable animal info string
     */
    private String formatAnimal(Animal animal) {
        String vetName = (animal.getVeterinarian() == null) ? "None" : animal.getVeterinarian().getName();
        String vaccineDate = (animal.getNextVaccineDate() == null) ? "None" : animal.getNextVaccineDate().toString();

        return "Animal Key: " + animal.getKey() + "\n"
                + "Name: " + animal.getName() + "\n"
                + "Species: " + animal.getSpecies().getName() + "\n"
                + "Birth Date: " + animal.getBirthDate() + "\n"
                + "Identification: " + animal.getIdentification() + "\n"
                + "Veterinarian: " + vetName + "\n"
                + "Next Vaccine Date: " + vaccineDate + "\n"
                + "Vaccinated Today: " + (animal.isVaccinatedToday() ? "Yes" : "No") + "\n"
                + "Dead: " + (animal.isDead() ? "Yes" : "No");
    }

    /**
     * Formats an employee for GUI display.
     *
     * @param employee the employee to format
     * @return readable employee info string
     */
    private String formatEmployee(Employee employee) {
        String role = employee.getClass().getSimpleName();

        return "Employee Key: " + employee.getKey() + "\n"
                + "Name: " + employee.getName() + "\n"
                + "Role: " + role + "\n"
                + "Satisfaction: " + employee.calculateSatisfaction();
    }

    /**
     * Formats a species for GUI display.
     *
     * @param species the species to format
     * @return readable species info string
     */
    private String formatSpecies(Species species) {
        String caretakerName = (species.getCaretaker() == null) ? "None" : species.getCaretaker().getName();

        return "Species Key: " + species.getKey() + "\n"
                + "Name: " + species.getName() + "\n"
                + "Danger Level: " + species.getDangerLevel() + "\n"
                + "Caretaker: " + caretakerName + "\n"
                + "Number of Animals: " + species.getAnimals().size();
    }

    /**
     * Formats a vaccine for GUI display.
     *
     * @param vaccine the vaccine to format
     * @return readable vaccine info string
     */
    private String formatVaccine(Vaccine vaccine) {
        return "Vaccine Key: " + vaccine.getKey() + "\n"
                + "Name: " + vaccine.getName() + "\n"
                + "Target Species: " + vaccine.getTargetSpecies().getName() + "\n"
                + "Administration Age: " + vaccine.getAdministrationAge();
    }

    /**
     * Shows all current alerts in the Alerts & Date tab.
     */
    private void showAlerts() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALERTS ===\n\n");

        sb.append("Low Satisfaction Employees\n");
        sb.append(separator());
        ArrayList<Employee> lowEmployees = hotel.getLowSatisfactionEmployees();
        if (lowEmployees.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Employee employee : lowEmployees) {
                sb.append(employee.getKey())
                        .append(" - ")
                        .append(employee.getName())
                        .append(" (")
                        .append(employee.calculateSatisfaction())
                        .append(")\n");
            }
        }

        sb.append("\nAnimals Without Veterinarian\n");
        sb.append(separator());
        ArrayList<Animal> animalsWithoutVet = hotel.getAnimalsWithoutVeterinarian();
        if (animalsWithoutVet.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Animal animal : animalsWithoutVet) {
                sb.append(animal.getKey())
                        .append(" - ")
                        .append(animal.getName())
                        .append("\n");
            }
        }

        sb.append("\nSpecies Without Caretaker\n");
        sb.append(separator());
        ArrayList<Species> speciesWithoutCaretaker = hotel.getSpeciesWithoutCaretaker();
        if (speciesWithoutCaretaker.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Species species : speciesWithoutCaretaker) {
                sb.append(species.getKey())
                        .append(" - ")
                        .append(species.getName())
                        .append("\n");
            }
        }

        alertsDateOutputArea.setText(sb.toString());
    }

    /**
     * Displays all animals in the Animals tab.
     */
    private void listAnimals() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ANIMALS ===\n\n");

        ArrayList<Animal> animals = hotel.getAnimals();
        if (animals.isEmpty()) {
            sb.append("No animals found.\n");
        } else {
            for (Animal animal : animals) {
                sb.append(separator());
                sb.append(formatAnimal(animal)).append("\n");
            }
        }

        animalsOutputArea.setText(sb.toString());
    }

    /**
     * Displays only veterinarians in the Employees tab.
     */
    private void listVeterinarians() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VETERINARIANS ===\n\n");

        ArrayList<Employee> employees = hotel.getEmployees();
        boolean any = false;
        for (Employee employee : employees) {
            if (employee instanceof Veterinarian) {
                any = true;
                sb.append(separator());
                sb.append(formatEmployee(employee)).append("\n");
            }
        }
        if (!any) {
            sb.append("None\n");
        }

        employeesOutputArea.setText(sb.toString());
    }

    /**
     * Displays only caretakers in the Employees tab.
     */
    private void listCaretakers() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CARETAKERS ===\n\n");

        ArrayList<Employee> employees = hotel.getEmployees();
        boolean any = false;
        for (Employee employee : employees) {
            if (employee instanceof Caretaker) {
                any = true;
                sb.append(separator());
                sb.append(formatEmployee(employee)).append("\n");
            }
        }
        if (!any) {
            sb.append("None\n");
        }

        employeesOutputArea.setText(sb.toString());
    }

    /**
     * Refreshes the Employees tab with both roles after add/remove (internal use).
     */
    private void refreshEmployeesTabAfterMutation() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EMPLOYEES ===\n\n");

        ArrayList<Employee> employees = hotel.getEmployees();
        if (employees.isEmpty()) {
            sb.append("No employees found.\n");
            employeesOutputArea.setText(sb.toString());
            return;
        }

        sb.append("--- Veterinarians ---\n");
        boolean anyVeterinarian = false;
        for (Employee employee : employees) {
            if (employee instanceof Veterinarian) {
                anyVeterinarian = true;
                sb.append(separator());
                sb.append(formatEmployee(employee)).append("\n");
            }
        }
        if (!anyVeterinarian) {
            sb.append("None\n");
        }

        sb.append("\n--- Caretakers ---\n");
        boolean anyCaretaker = false;
        for (Employee employee : employees) {
            if (employee instanceof Caretaker) {
                anyCaretaker = true;
                sb.append(separator());
                sb.append(formatEmployee(employee)).append("\n");
            }
        }
        if (!anyCaretaker) {
            sb.append("None\n");
        }

        employeesOutputArea.setText(sb.toString());
    }

    /**
     * Shows average employee happiness in the Employees tab.
     */
    private void showAverageHappiness() {
        employeesOutputArea.setText("=== AVERAGE EMPLOYEE HAPPINESS ===\n\n" + calculateAverageHappiness());
    }

    /**
     * Shows the current hotel date in the Alerts & Date tab.
     */
    private void showCurrentDate() {
        alertsDateOutputArea.setText("=== CURRENT HOTEL DATE ===\n\n" + hotel.getCurrentDate());
    }

    /**
     * Advances the date by one day and refreshes the Home tab.
     */
    private void advanceOneDay() {
        hotel.advanceOneDay();
        alertsDateOutputArea.setText("=== DATE UPDATED ===\n\nCurrent hotel date: " + hotel.getCurrentDate());
        refreshHomeTab();
    }

    /**
     * Adds a new animal through input dialogs.
     * Result is shown in the Animals tab.
     */
    private void addAnimal() {
        try {
            String key = JOptionPane.showInputDialog(this, "Enter animal key:");
            if (key == null) return;

            String name = JOptionPane.showInputDialog(this, "Enter animal name:");
            if (name == null) return;

            String speciesKey = JOptionPane.showInputDialog(this, "Enter species key:");
            if (speciesKey == null) return;

            Species species = hotel.findSpeciesByKey(speciesKey);
            if (species == null) {
                JOptionPane.showMessageDialog(this, "Species not found.");
                return;
            }

            String birthDateText = JOptionPane.showInputDialog(this, "Enter birth date (YYYY-MM-DD):");
            if (birthDateText == null) return;
            LocalDate birthDate = LocalDate.parse(birthDateText);

            String identification = JOptionPane.showInputDialog(this, "Enter identification:");
            if (identification == null) return;

            Animal animal = new Animal(key, name, species, birthDate, identification);
            hotel.addAnimal(animal);

            JOptionPane.showMessageDialog(this, "Animal added successfully.");
            listAnimals();
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new employee through input dialogs.
     * Result is shown in the Employees tab.
     */
    private void addEmployee() {
        try {
            String type = JOptionPane.showInputDialog(this, "Enter employee type (caretaker/care/veterinarian/vet):");
            if (type == null) return;

            String key = JOptionPane.showInputDialog(this, "Enter employee key:");
            if (key == null) return;

            String name = JOptionPane.showInputDialog(this, "Enter employee name:");
            if (name == null) return;

            String normalizedType = type.trim().toLowerCase();
            boolean isCaretaker = normalizedType.equals("caretaker") || normalizedType.equals("care");
            boolean isVet = normalizedType.equals("veterinarian") || normalizedType.equals("vet");

            if (isCaretaker && !key.matches("C\\d+")) {
                JOptionPane.showMessageDialog(this, "Caretaker keys must follow C1, C2, ...");
                return;
            }
            if (isVet && !key.matches("V\\d+")) {
                JOptionPane.showMessageDialog(this, "Veterinarian keys must follow V1, V2, ...");
                return;
            }

            if (isCaretaker) {
                hotel.addEmployee(new Caretaker(key, name));
                JOptionPane.showMessageDialog(this, "Caretaker added successfully.");
            } else if (isVet) {
                hotel.addEmployee(new Veterinarian(key, name));
                JOptionPane.showMessageDialog(this, "Veterinarian added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid employee type. Use caretaker/care or veterinarian/vet.");
                return;
            }

            refreshEmployeesTabAfterMutation();
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Searches for an animal by key.
     */
    private void searchAnimalByKey() {
        String key = JOptionPane.showInputDialog(this, "Enter animal key:");
        if (key == null) return;

        Animal animal = hotel.findAnimalByKey(key);
        if (animal == null) {
            animalsOutputArea.setText("Animal not found.");
        } else {
            animalsOutputArea.setText("=== ANIMAL FOUND ===\n\n" + formatAnimal(animal));
        }
    }

    /**
     * Searches for an employee by key.
     */
    private void searchEmployeeByKey() {
        String key = JOptionPane.showInputDialog(this, "Enter employee key:");
        if (key == null) return;

        Employee employee = hotel.findEmployeeByKey(key);
        if (employee == null) {
            employeesOutputArea.setText("Employee not found.");
        } else {
            employeesOutputArea.setText("=== EMPLOYEE FOUND ===\n\n" + formatEmployee(employee));
        }
    }

    /**
     * Assigns a veterinarian to an animal.
     */
    private void assignVeterinarianToAnimal() {
        try {
            String vetKey = JOptionPane.showInputDialog(this, "Enter veterinarian key:");
            if (vetKey == null) return;

            String animalKey = JOptionPane.showInputDialog(this, "Enter animal key:");
            if (animalKey == null) return;

            boolean success = hotel.assignVeterinarianToAnimal(vetKey, animalKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Veterinarian assigned successfully.");
                listAnimals();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Assignment failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Assigns a caretaker to a species.
     */
    private void assignCaretakerToSpecies() {
        try {
            String caretakerKey = JOptionPane.showInputDialog(this, "Enter caretaker key:");
            if (caretakerKey == null) return;

            String speciesKey = JOptionPane.showInputDialog(this, "Enter species key:");
            if (speciesKey == null) return;

            boolean success = hotel.assignCaretakerToSpecies(caretakerKey, speciesKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Caretaker assigned successfully.");
                speciesOutputArea.setText("=== SPECIES UPDATED ===\n\n" + formatSpecies(hotel.findSpeciesByKey(speciesKey)));
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Assignment failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Vaccinates an animal.
     */
    private void vaccinateAnimal() {
        try {
            String animalKey = JOptionPane.showInputDialog(this, "Enter animal key:");
            if (animalKey == null) return;

            String vaccineKey = JOptionPane.showInputDialog(this, "Enter vaccine key:");
            if (vaccineKey == null) return;

            boolean success = hotel.vaccinateAnimal(animalKey, vaccineKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Animal vaccinated successfully.");
                listAnimals();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Vaccination failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Searches for animals by name.
     */
    private void searchAnimalByName() {
        String name = JOptionPane.showInputDialog(this, "Enter animal name:");
        if (name == null) return;

        ArrayList<Animal> results = hotel.searchAnimalsByName(name);

        if (results.isEmpty()) {
            animalsOutputArea.setText("No animals found with name: " + name);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== ANIMALS FOUND ===\n\n");
        for (Animal animal : results) {
            sb.append(separator());
            sb.append(formatAnimal(animal)).append("\n");
        }

        animalsOutputArea.setText(sb.toString());
    }

    /**
     * Searches for employees by name.
     */
    private void searchEmployeeByName() {
        String name = JOptionPane.showInputDialog(this, "Enter employee name:");
        if (name == null) return;

        ArrayList<Employee> results = hotel.searchEmployeesByName(name);

        if (results.isEmpty()) {
            employeesOutputArea.setText("No employees found with name: " + name);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== EMPLOYEES FOUND ===\n\n");
        for (Employee employee : results) {
            sb.append(separator());
            sb.append(formatEmployee(employee)).append("\n");
        }

        employeesOutputArea.setText(sb.toString());
    }

    /**
     * Adds a new species.
     */
    private void addSpecies() {
        try {
            String key = JOptionPane.showInputDialog(this, "Enter species key:");
            if (key == null) return;

            String name = JOptionPane.showInputDialog(this, "Enter species name:");
            if (name == null) return;

            String dangerText = JOptionPane.showInputDialog(this, "Enter danger level (0 to 1):");
            if (dangerText == null) return;

            double dangerLevel = Double.parseDouble(dangerText);

            Species species = new Species(key, name, dangerLevel);
            hotel.addSpecies(species);

            JOptionPane.showMessageDialog(this, "Species added successfully.");
            speciesOutputArea.setText("=== SPECIES ADDED ===\n\n" + formatSpecies(species));
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Sets a specific hotel date.
     */
    private void setSpecificDate() {
        try {
            String dateText = JOptionPane.showInputDialog(this, "Enter new date (YYYY-MM-DD):");
            if (dateText == null) return;

            LocalDate newDate = LocalDate.parse(dateText);
            hotel.setCurrentDate(newDate);

            alertsDateOutputArea.setText("=== DATE CHANGED ===\n\nCurrent hotel date: " + hotel.getCurrentDate());
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new vaccine.
     */
    private void addVaccine() {
        try {
            String key = JOptionPane.showInputDialog(this, "Enter vaccine key:");
            if (key == null) return;

            String name = JOptionPane.showInputDialog(this, "Enter vaccine name:");
            if (name == null) return;

            String speciesKey = JOptionPane.showInputDialog(this, "Enter target species key:");
            if (speciesKey == null) return;

            Species species = hotel.findSpeciesByKey(speciesKey);
            if (species == null) {
                JOptionPane.showMessageDialog(this, "Species not found.");
                return;
            }

            String ageText = JOptionPane.showInputDialog(this, "Enter administration age:");
            if (ageText == null) return;

            int administrationAge = Integer.parseInt(ageText);

            Vaccine vaccine = new Vaccine(key, name, species, administrationAge);
            hotel.addVaccine(vaccine);

            JOptionPane.showMessageDialog(this, "Vaccine added successfully.");
            listVaccines();
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Lists all vaccines.
     */
    private void listVaccines() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VACCINES ===\n\n");

        ArrayList<Vaccine> vaccines = hotel.getVaccines();
        if (vaccines.isEmpty()) {
            sb.append("No vaccines found.\n");
        } else {
            for (Vaccine vaccine : vaccines) {
                sb.append(separator());
                sb.append(formatVaccine(vaccine)).append("\n");
            }
        }

        vaccinesOutputArea.setText(sb.toString());
    }

    /**
     * Searches for a species by key.
     */
    private void searchSpeciesByKey() {
        String key = JOptionPane.showInputDialog(this, "Enter species key:");
        if (key == null) return;

        Species species = hotel.findSpeciesByKey(key);
        if (species == null) {
            speciesOutputArea.setText("Species not found.");
        } else {
            speciesOutputArea.setText("=== SPECIES FOUND ===\n\n" + formatSpecies(species));
        }
    }

    /**
     * Searches for species by name.
     */
    private void searchSpeciesByName() {
        String name = JOptionPane.showInputDialog(this, "Enter species name:");
        if (name == null) return;

        ArrayList<Species> results = hotel.searchSpeciesByName(name);

        if (results.isEmpty()) {
            speciesOutputArea.setText("No species found with name: " + name);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== SPECIES FOUND ===\n\n");
        for (Species species : results) {
            sb.append(separator());
            sb.append(formatSpecies(species)).append("\n");
        }

        speciesOutputArea.setText(sb.toString());
    }

    /**
     * Lists all species.
     */
    private void listSpecies() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SPECIES ===\n\n");

        ArrayList<Species> speciesList = hotel.getSpeciesList();
        if (speciesList.isEmpty()) {
            sb.append("No species found.\n");
        } else {
            for (Species species : speciesList) {
                sb.append(separator());
                sb.append(formatSpecies(species)).append("\n");
            }
        }

        speciesOutputArea.setText(sb.toString());
    }

    /**
     * Removes a veterinarian assignment from an animal.
     */
    private void removeVeterinarianFromAnimal() {
        try {
            String animalKey = JOptionPane.showInputDialog(this, "Enter animal key:");
            if (animalKey == null) return;

            boolean success = hotel.removeVeterinarianFromAnimal(animalKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Veterinarian removed successfully.");
                listAnimals();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "No veterinarian removed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Removes a caretaker assignment from a species.
     */
    private void removeCaretakerFromSpecies() {
        try {
            String speciesKey = JOptionPane.showInputDialog(this, "Enter species key:");
            if (speciesKey == null) return;

            boolean success = hotel.removeCaretakerFromSpecies(speciesKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Caretaker removed successfully.");
                speciesOutputArea.setText("=== SPECIES UPDATED ===\n\n" + formatSpecies(hotel.findSpeciesByKey(speciesKey)));
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "No caretaker removed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Removes a species.
     */
    private void removeSpecies() {
        try {
            String speciesKey = JOptionPane.showInputDialog(this, "Enter species key:");
            if (speciesKey == null) return;

            Species species = hotel.findSpeciesByKey(speciesKey);
            if (species == null) {
                JOptionPane.showMessageDialog(this, "Species not found.");
                return;
            }

            if (!species.getAnimals().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cannot remove species that still has animals.");
                return;
            }

            boolean success = hotel.removeSpeciesByKey(speciesKey);
            if (success) {
                JOptionPane.showMessageDialog(this, "Species removed successfully.");
                showSpeciesWithoutCaretaker();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Species removal failed.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Removes an animal.
     */
    private void removeAnimal() {
        try {
            String animalKey = JOptionPane.showInputDialog(this, "Enter animal key:");
            if (animalKey == null) return;

            boolean success = hotel.removeAnimalByKey(animalKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Animal removed successfully.");
                listAnimals();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Animal not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Removes an employee.
     */
    private void removeEmployee() {
        try {
            String employeeKey = JOptionPane.showInputDialog(this, "Enter employee key:");
            if (employeeKey == null) return;

            boolean success = hotel.removeEmployeeByKey(employeeKey);

            if (success) {
                JOptionPane.showMessageDialog(this, "Employee removed successfully.");
                refreshEmployeesTabAfterMutation();
                refreshHomeTab();
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Shows vaccination reminders in the Vaccines tab.
     */
    private void showVaccinationReminders() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== VACCINATION REMINDERS ===\n\n");
        sb.append("Date: ").append(hotel.getCurrentDate()).append("\n\n");

        ArrayList<Animal> dueAnimals = hotel.getAnimalsDueForVaccinationToday();

        if (dueAnimals.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Animal animal : dueAnimals) {
                sb.append(separator());
                sb.append("Animal: ").append(animal.getName()).append(" (").append(animal.getKey()).append(")\n");

                Vaccine dueVaccine = hotel.getDueVaccineForAnimal(animal);
                if (dueVaccine != null) {
                    sb.append("Required Vaccine: ").append(dueVaccine.getName()).append("\n");
                } else {
                    sb.append("Required Vaccine: No matching vaccine found\n");
                }
            }
        }

        vaccinesOutputArea.setText(sb.toString());
    }

    /**
     * Shows animals that died today due to missed vaccination.
     */
    private void showAnimalsDeadToday() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ANIMALS DEAD TODAY ===\n\n");

        ArrayList<Animal> deadToday = hotel.getAnimalsDeadToday();

        if (deadToday.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Animal animal : deadToday) {
                sb.append(separator());
                sb.append("Animal: ").append(animal.getName()).append("\n");
                sb.append("Key: ").append(animal.getKey()).append("\n");
                sb.append("Species: ").append(animal.getSpecies().getName()).append("\n");
            }
        }

        alertsDateOutputArea.setText(sb.toString());
        refreshHomeTab();
    }

    /**
     * Placeholder GUI action for loading vaccines from file.
     * Real parsing depends on the professor's file format.
     */
    private void loadVaccinesFile() {
        String filename = JOptionPane.showInputDialog(this, "Enter vaccine file name:");
        if (filename == null) return;

        try {
            String resultMessage = hotel.loadVaccinesFromFile(filename);
            JOptionPane.showMessageDialog(this, resultMessage);
            listVaccines();
            refreshHomeTab();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            hotel.saveStateToFile(AppDataLoader.SAVE_FILE);
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + AppDataLoader.SAVE_FILE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    /**
     * Shows species that currently have no caretaker.
     */
    private void showSpeciesWithoutCaretaker() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SPECIES WITHOUT CARETAKER ===\n\n");

        ArrayList<Species> speciesList = hotel.getSpeciesWithoutCaretaker();

        if (speciesList.isEmpty()) {
            sb.append("None\n");
        } else {
            for (Species species : speciesList) {
                sb.append(separator());
                sb.append(formatSpecies(species)).append("\n");
            }
        }

        speciesOutputArea.setText(sb.toString());
    }
}