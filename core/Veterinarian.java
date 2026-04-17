package core;

import java.util.ArrayList;

//veterenarian class is a subclass of employee
//a veterinarian is an employee
public class Veterinarian extends Employee {
    private ArrayList<Animal> assignedAnimals;     //assignedAnimals stores many animal objects that are assigned to this veterinarian
    
    public Veterinarian(String key, String name) {
        super(key, name);          //super means call the constructor of the parent class (employee) to assign key and name
        if (!key.matches("V\\d+")) {
            throw new IllegalArgumentException("Veterinarian key must follow format V1, V2, ...");
        }
        assignedAnimals = new ArrayList<>();
    }

    public ArrayList<Animal> getAssignedAnimals() {
        return assignedAnimals;
    }

    public void addAnimal(Animal animal) {
        if  (!assignedAnimals.contains(animal)) {       //checks if animal is already in the list to avoid duplicates
            assignedAnimals.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        assignedAnimals.remove(animal);
    }

    //helper method to check whether this vet is responsible for at least one animal of a species
    private boolean hasSpecies(Species species) {
        for (Animal animal : assignedAnimals) {
            if (animal.getSpecies() == species) {
                return true;
            }
        }
        return false;
    }

    // counts how many vets are responsible for that species
    private int countVeterinariansForSpecies(Species species) {
        int count = 0;

        for (Animal animal : species.getAnimals()) {
            if (animal.getVeterinarian() != null && animal.getVeterinarian().hasSpecies(species)){
                count++;
            }
        }

        return count;
    }

    @Override
    public double calculateSatisfaction() {
        double work = 0.0;
        //prevents double counting of animals from the same species by keeping track of which species have already been counted
        ArrayList<Species> countedSpecies = new ArrayList<>();

        for (Animal animal : assignedAnimals) {
            Species species = animal.getSpecies();

            if (!countedSpecies.contains(species)) {
                countedSpecies.add(species);

                int population = species.getAnimals().size();
                int veterinariansForSpecies = countVeterinariansForSpecies(species);

                if (veterinariansForSpecies > 0) {
                    work += (double) population / veterinariansForSpecies;
                }
            }
        }
    
        return 20 - work; // Each animal reduces satisfaction by 1, but the reduction is shared among veterinarians treating the same species
    }
}
