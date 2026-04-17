package core;       //employee class belongs to the core package

//employee class is an abstract class because it has an abstract method (calculateSatisfaction) that will be implemented differently in the subclasses (Caretaker and Veterinarian). it cannot be instantiated directly, only through its subclasses
public abstract class Employee {
    private String key;         //field to store unique key. private means only emplyee class can access this field
    private String name;        //field to store name

    public Employee(String key, String name) {          // constructor to build an object
        
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Employee key cannot be empty.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }
        
        this.key = key;             //assign paraeter key into this object's key field
        this.name = name;
    }

    public String getKey() {            //public method sends the value of key back as a string
        return key;
    }

    public String getName() {
        return name;
    }
    
    public abstract double calculateSatisfaction();     //abstract method to calculate the satisfaction of an employee. it is abstract because it will be implemented differently in the subclasses (Caretaker and Veterinarian)
    @Override
    public String toString() {
    return "Employee{key='" + key + "', name='" + name + "'}";
    }
}
