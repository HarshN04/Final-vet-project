package ui;

import core.VetHotel;

import javax.swing.SwingUtilities;

/**
 * Main launcher for the GUI version of the Vet Hotel system.
 */
public class Main {
    public static void main(String[] args) {
        VetHotel hotel = new VetHotel();

        AppDataLoader.loadSavedDataOrSeed(hotel);

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(hotel);
            window.setVisible(true);
        });
    }
}
