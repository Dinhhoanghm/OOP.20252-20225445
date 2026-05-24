package vn.oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateView extends JFrame {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("'Ngày' dd 'tháng' MM 'năm' yyyy");

    private final JTextField dateDisplay;
    private final JTextField inputField;
    private final JLabel errorLabel;

    private final JButton daysBefore;
    private final JButton daysAfter;
    private final JButton monthsBefore;
    private final JButton monthsAfter;

    public DateView() {
        setTitle("Hiển Thị Ngày");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout(0, 14));
        main.setBackground(new Color(245, 245, 250));
        main.setBorder(new EmptyBorder(22, 26, 22, 26));

        // ── date display ──────────────────────────────────────────────────────
        dateDisplay = new JTextField();
        dateDisplay.setEditable(false);
        dateDisplay.setFont(new Font("SansSerif", Font.BOLD, 22));
        dateDisplay.setHorizontalAlignment(JTextField.CENTER);
        dateDisplay.setBackground(Color.WHITE);
        dateDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 210), 1),
                new EmptyBorder(10, 8, 10, 8)));

        // ── input row ─────────────────────────────────────────────────────────
        JPanel inputRow = new JPanel(new BorderLayout(10, 0));
        inputRow.setBackground(new Color(245, 245, 250));

        JLabel inputLabel = new JLabel("Nhập số x:");
        inputLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 210), 1),
                new EmptyBorder(6, 8, 6, 8)));

        inputRow.add(inputLabel, BorderLayout.WEST);
        inputRow.add(inputField, BorderLayout.CENTER);

        // ── buttons ───────────────────────────────────────────────────────────
        daysBefore   = makeButton("◀  x ngày",   new Color(66, 133, 244),  Color.WHITE);
        daysAfter    = makeButton("x ngày  ▶",   new Color(52, 168,  83),  Color.WHITE);
        monthsBefore = makeButton("◀  x tháng",  new Color(251, 188,  4),  Color.DARK_GRAY);
        monthsAfter  = makeButton("x tháng  ▶",  new Color(234,  67,  53), Color.WHITE);

        JPanel btnGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        btnGrid.setBackground(new Color(245, 245, 250));
        btnGrid.add(daysBefore);
        btnGrid.add(daysAfter);
        btnGrid.add(monthsBefore);
        btnGrid.add(monthsAfter);

        // ── error label ───────────────────────────────────────────────────────
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        errorLabel.setForeground(new Color(210, 40, 40));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // ── layout ────────────────────────────────────────────────────────────
        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setBackground(new Color(245, 245, 250));
        center.add(inputRow,   BorderLayout.NORTH);
        center.add(btnGrid,    BorderLayout.CENTER);
        center.add(errorLabel, BorderLayout.SOUTH);

        main.add(dateDisplay, BorderLayout.NORTH);
        main.add(center,      BorderLayout.CENTER);

        add(main);
        pack();
        setMinimumSize(new Dimension(420, 280));
        setLocationRelativeTo(null);
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setPreferredSize(new Dimension(0, 46));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ── public interface for the controller ───────────────────────────────────

    public void updateDate(LocalDate date) {
        dateDisplay.setText(date.format(FORMATTER));
    }

    public String getInputText() {
        return inputField.getText().trim();
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    public void clearError() {
        errorLabel.setText(" ");
    }

    public void addDaysBeforeListener(ActionListener l)   { daysBefore.addActionListener(l); }
    public void addDaysAfterListener(ActionListener l)    { daysAfter.addActionListener(l); }
    public void addMonthsBeforeListener(ActionListener l) { monthsBefore.addActionListener(l); }
    public void addMonthsAfterListener(ActionListener l)  { monthsAfter.addActionListener(l); }
}
