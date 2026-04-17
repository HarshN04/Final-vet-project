package ui;

import core.Animal;
import core.Caretaker;
import core.Species;
import core.VetHotel;
import core.Veterinarian;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

/**
 * Utility class used to load sample data into the VetHotel system.
 * This avoids duplicating the same seed data code in Main and MainCLI.
 */
public class AppDataLoader {
    public static final String SAVE_FILE = "hotel_save_data.txt";

    public static void loadSavedDataOrSeed(VetHotel hotel) {
        Path savePath = Path.of(SAVE_FILE);
        if (Files.exists(savePath)) {
            try {
                hotel.loadStateFromFile(SAVE_FILE);
                return;
            } catch (Exception e) {
                System.out.println("Saved data load failed, using seed data instead: " + e.getMessage());
            }
        }

        seedTestData(hotel);
    }

    /**
     * Loads sample species, animals, employees, vaccines, and assignments.
     *
     * @param hotel the VetHotel system to populate
     */
    public static void seedTestData(VetHotel hotel) {
        try {
            Species dog = new Species("S1", "Canis lupus", 0.2);
            Species cat = new Species("S2", "Felis catue", 0.1);
            Species horse = new Species("S3", "caballus", 0.4);
            Species cow = new Species("S4", "Bos taurus", 0.5);

            hotel.addSpecies(dog);
            hotel.addSpecies(cat);
            hotel.addSpecies(horse);
            hotel.addSpecies(cow);

            Animal a1 = new Animal("A1", "Buddy", dog, LocalDate.of(2020, 5, 1), "ID123");
            Animal a2 = new Animal("A2", "Milo", cat, LocalDate.of(2022, 8, 10), "ID124");
            Animal a3 = new Animal("A3", "Joseph", horse, LocalDate.of(2019, 11, 20), "ID125");
            Animal a4 = new Animal("A4", "Molly", cow, LocalDate.of(2020, 5, 1), "ID126");

            hotel.addAnimal(a1);
            hotel.addAnimal(a2);
            hotel.addAnimal(a3);
            hotel.addAnimal(a4);

            Caretaker c1 = new Caretaker("C1", "Alice");
            Veterinarian v1 = new Veterinarian("V1", "Bob");
            Veterinarian v2 = new Veterinarian("V2", "Sara");

            hotel.addEmployee(c1);
            hotel.addEmployee(v1);
            hotel.addEmployee(v2);

            hotel.assignCaretakerToSpecies("C1", "S1");
            hotel.assignVeterinarianToAnimal("V1", "A1");
            hotel.assignVeterinarianToAnimal("V2", "A2");

            hotel.setAnimalNextVaccineDate("A1", LocalDate.of(2026, 1, 1));
            hotel.setAnimalNextVaccineDate("A2", LocalDate.of(2026, 1, 1));
            hotel.setAnimalNextVaccineDate("A3", LocalDate.of(2026, 1, 2));

        } catch (Exception e) {
            System.out.println("Seed data error: " + e.getMessage());
        }
    }
}
