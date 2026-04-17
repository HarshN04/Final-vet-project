package core;

public class Vaccine {
    private String key;
    private String name;
    private Species targetSpecies;
    private int administrationAge;

    public Vaccine(String key, String name, Species targetSpecies, int administrationAge) {
        
        
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Vaccine key cannot be empty.");
        }
        if (!key.matches("VAC\\d+")) {
            throw new IllegalArgumentException("Vaccine key must follow format VAC1, VAC2, ...");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Vaccine name cannot be empty.");
        }
        if (targetSpecies == null) {
            throw new IllegalArgumentException("Vaccine target species cannot be null.");
        }
        if (administrationAge < 0) {
            throw new IllegalArgumentException("Vaccine administration age cannot be negative.");
        }

        this.key = key;
        this.name = name;
        this.targetSpecies = targetSpecies;
        this.administrationAge = administrationAge;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Species getTargetSpecies() {
        return targetSpecies;
    }

    public int getAdministrationAge() {
        return administrationAge;
    }

    @Override
    public String toString() {
        return "Vaccine{key='" + key + "', name='" + name + "', targetSpecies='"
                + targetSpecies.getName() + "', administrationAge=" + administrationAge + "}";
    }
}