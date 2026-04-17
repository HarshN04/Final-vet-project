package ui;

import core.VetHotel;

/**
 * Main launcher for the command-line version of the Vet Hotel system.
 */
public class MainCLI {
    public static void main(String[] args) {
        VetHotel hotel = new VetHotel();

        AppDataLoader.loadSavedDataOrSeed(hotel);

        CommandLineUI cli = new CommandLineUI(hotel);
        cli.start();
    }
}
