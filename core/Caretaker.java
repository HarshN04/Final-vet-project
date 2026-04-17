package core;

import java.util.ArrayList;

//extends employee means Caretaker inherits from Employee
//caretaker class is a subclass of employee class. it can access the fields and methods of employee class
// a caretaker is an employee
public class Caretaker extends Employee {           
    private ArrayList<Species> assignedSpecies;     //assignedSpecies stores many species objects that are assigned to this caretaker

    public Caretaker(String key, String name) {
        super(key, name);          //super means call the constructor of the parent class (employee) to assign key and name
        if (!key.matches("C\\d+")) {
            throw new IllegalArgumentException("Caretaker key must follow format C1, C2, ...");
        }
        assignedSpecies = new ArrayList<>();
    }

    public ArrayList<Species> getAssignedSpecies() {
        return assignedSpecies;
    }

    public void addSpecies(Species species) {
        if (!assignedSpecies.contains(species)) {
            assignedSpecies.add(species);
        }
    }

    public void removeSpecies(Species species) {
        assignedSpecies.remove(species);
    }

    @Override
    public double calculateSatisfaction() {
        double total = 20;

        for (Species species : assignedSpecies) {
            total -= species.getDangerLevel();      // each assigned species reduces satisfaction by its danger level
        }

        return total;
    }
}
