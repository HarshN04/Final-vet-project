package core;

import java.time.LocalDate;
import java.time.Period;

public class Animal {
    private String key;
    private String name;
    private Species species;
    private LocalDate birthDate;
    private String identification;
    private Veterinarian veterinarian;

    private LocalDate nextVaccineDate;
    private boolean vaccinatedToday;
    private boolean dead;

    public Animal(String key, String name, Species species, LocalDate birthDate, String identification) {
        
        
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Animal key cannot be empty.");
        }
        if (!key.matches("A\\d+")) {
            throw new IllegalArgumentException("Animal key must follow format A1, A2, ...");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Animal name cannot be empty.");
        }
        if (species == null) {
            throw new IllegalArgumentException("Animal species cannot be null.");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Animal birth date cannot be null.");
        }
        if (identification == null || identification.isBlank()) {
            throw new IllegalArgumentException("Animal identification cannot be empty.");
        }

        this.key = key;
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
        this.identification = identification;
        this.veterinarian = null;

        this.nextVaccineDate = null;
        this.vaccinatedToday = false;
        this.dead = false;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIdentification() {
        return identification;
    }

    public Veterinarian getVeterinarian() {
        return veterinarian;
    }    

    public void setVeterinarian(Veterinarian veterinarian) {
        this.veterinarian = veterinarian;
    }

    public LocalDate getNextVaccineDate() {
        return nextVaccineDate;
    }

    public void setNextVaccineDate(LocalDate nextVaccineDate) {
        this.nextVaccineDate = nextVaccineDate;
    }

    public boolean isVaccinatedToday() {
        return vaccinatedToday;
    }

    public void setVaccinatedToday(boolean vaccinatedToday) {
        this.vaccinatedToday = vaccinatedToday;
    }
  
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getAgeInYears(LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }

    @Override
    public String toString() {
        String vetName = (veterinarian == null) ? "None" : veterinarian.getName();

        return "Animal{"
                + "key='" + key + '\''
                + ", name='" + name + '\''
                + ", species='" + species.getName() + '\''
                + ", birthDate=" + birthDate
                + ", identification='" + identification + '\''
                + ", nextVaccineDate=" + nextVaccineDate
                + ", vaccinatedToday=" + vaccinatedToday
                + ", dead=" + dead
                + ", veterinarian='" + vetName + '\''
                + "}";
    }
}
