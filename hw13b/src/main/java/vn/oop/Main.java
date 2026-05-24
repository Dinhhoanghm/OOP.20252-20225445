package vn.oop;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DateModel model = new DateModel();
            DateView view = new DateView();
            new DateController(model, view);
            view.setVisible(true);
        });
    }
}
