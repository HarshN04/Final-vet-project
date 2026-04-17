package core;               //species class belongs to the core package

import java.util.ArrayList;

public class Species {
    //fields of the species class. only the species class can access them
    private String key;         
    private String name;
    private double dangerLevel;
    private ArrayList<Animal> animals;      //field to store the list of animals of this species. it is an array list of Animal objects
    private Caretaker caretaker;

    public Species(String key, String name, double dangerLevel) {           //constructor to build an object
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Species key cannot be empty.");
        }
        if (!key.matches("S\\d+")) {
            throw new IllegalArgumentException("Species key must follow format S1, S2, ...");
        }
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Species name cannot be empty.");
        }
        if (dangerLevel < 0 || dangerLevel > 1) {
            throw new IllegalArgumentException("Species danger level must be between 0 and 1.");
        }
                
        this.key = key;         //assign parameter key into this object's key field
        this.name = name;       //assign parameter name into this object's name field
        this.dangerLevel = dangerLevel;     //assign parameter dangerLevel into this object's dangerLevel field
        this.animals = new ArrayList<>();  //initialize the animals list as an empty array list of Animal objects
        this.caretaker = null;
    }

    //public methods to send the value of the fields back as a string or double
    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public double getDangerLevel() {
        return dangerLevel;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    // method to add an animal to the animals list of this species. it takes an Animal object as a parameter and adds it to the animals list
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    // method to remove an animal from the animals list of this species. it takes an Animal object as a parameter and removes it from the animals list
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    // getter and setter for caretaker field. the getter returns the caretaker of this species and the setter assigns a caretaker to this species
    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    // toString method to return a string representation of the species object. it includes the key, name and danger level of the species
    @Override
    public String toString() {
        String caretakerName = (caretaker == null) ? "None" : caretaker.getName();
        return "Species{key='" + key + "', name='" + name + "', dangerLevel=" + dangerLevel + ", caretaker='" + caretakerName + "'}";
    }
}
