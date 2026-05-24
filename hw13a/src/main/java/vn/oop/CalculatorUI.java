package vn.oop;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CalculatorUI extends JFrame {

    private final Calculator calculator = new Calculator();

    private JLabel expressionLabel;
    private JTextField displayField;

    private double firstOperand;
    private String operator = "";
    private boolean waitingForSecond = false;
    private boolean justCalculated = false;
    private boolean hasError = false;

    private enum ButtonType { DIGIT, OPERATOR, EQUALS, CLEAR, DELETE, DECIMAL, NEGATE }

    public CalculatorUI() {
        initUI();
    }

    private void initUI() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.setBackground(new Color(28, 28, 30));
        main.setBorder(new EmptyBorder(12, 12, 12, 12));

        main.add(buildDisplayPanel(), BorderLayout.NORTH);
        main.add(buildButtonPanel(), BorderLayout.CENTER);

        add(main);
        pack();
        setMinimumSize(new Dimension(500, 640));
        setLocationRelativeTo(null);
    }

    private JPanel buildDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(new Color(44, 44, 46));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        expressionLabel = new JLabel(" ");
        expressionLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        expressionLabel.setForeground(new Color(142, 142, 147));
        expressionLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setFont(new Font("SansSerif", Font.BOLD, 40));
        displayField.setForeground(Color.WHITE);
        displayField.setBackground(new Color(44, 44, 46));
        displayField.setBorder(null);
        displayField.setHorizontalAlignment(JTextField.RIGHT);

        panel.add(expressionLabel, BorderLayout.NORTH);
        panel.add(displayField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 4, 8, 8));
        panel.setBackground(new Color(28, 28, 30));

        // Row 1
        panel.add(btn("C",   ButtonType.CLEAR));
        panel.add(btn("DEL", ButtonType.DELETE));
        panel.add(btn("%",   ButtonType.OPERATOR));
        panel.add(btn("/",   ButtonType.OPERATOR));
        // Row 2
        panel.add(btn("7", ButtonType.DIGIT));
        panel.add(btn("8", ButtonType.DIGIT));
        panel.add(btn("9", ButtonType.DIGIT));
        panel.add(btn("*", ButtonType.OPERATOR));
        // Row 3
        panel.add(btn("4", ButtonType.DIGIT));
        panel.add(btn("5", ButtonType.DIGIT));
        panel.add(btn("6", ButtonType.DIGIT));
        panel.add(btn("-", ButtonType.OPERATOR));
        // Row 4
        panel.add(btn("1", ButtonType.DIGIT));
        panel.add(btn("2", ButtonType.DIGIT));
        panel.add(btn("3", ButtonType.DIGIT));
        panel.add(btn("+", ButtonType.OPERATOR));
        // Row 5
        panel.add(btn("+/-", ButtonType.NEGATE));
        panel.add(btn("0",   ButtonType.DIGIT));
        panel.add(btn(".",   ButtonType.DECIMAL));
        panel.add(btn("=",   ButtonType.EQUALS));

        return panel;
    }

    private JButton btn(String text, ButtonType type) {
        Color bg = switch (type) {
            case CLEAR, DELETE -> new Color(215, 70, 70);
            case OPERATOR      -> new Color(255, 149, 0);
            case EQUALS        -> new Color(48, 186, 84);
            default            -> new Color(58, 58, 60);
        };

        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.PLAIN, 18));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(62, 62));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Color hover = bg.brighter();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(hover); }
            public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });

        b.addActionListener(e -> handle(text, type));
        return b;
    }

    // ── event handlers ────────────────────────────────────────────────────────

    private void handle(String text, ButtonType type) {
        if (hasError && type != ButtonType.CLEAR) return;
        switch (type) {
            case DIGIT    -> onDigit(text);
            case DECIMAL  -> onDecimal();
            case OPERATOR -> onOperator(text);
            case EQUALS   -> onEquals();
            case CLEAR    -> onClear();
            case DELETE   -> onDelete();
            case NEGATE   -> onNegate();
        }
    }

    private void onDigit(String d) {
        if (waitingForSecond || justCalculated) {
            displayField.setText("0".equals(d) ? "0" : d);
            waitingForSecond = false;
            justCalculated = false;
        } else {
            String cur = displayField.getText();
            displayField.setText("0".equals(cur) ? d : cur + d);
        }
    }

    private void onDecimal() {
        if (waitingForSecond || justCalculated) {
            displayField.setText("0.");
            waitingForSecond = false;
            justCalculated = false;
            return;
        }
        if (!displayField.getText().contains(".")) {
            displayField.setText(displayField.getText() + ".");
        }
    }

    private void onOperator(String op) {
        justCalculated = false;
        // chain: if an operator is already pending and the second number is in the display
        if (!operator.isEmpty() && !waitingForSecond) {
            onEquals();
            if (hasError) return;
        }
        try {
            firstOperand = Double.parseDouble(displayField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        operator = op;
        waitingForSecond = true;
        expressionLabel.setText(fmt(firstOperand) + " " + op);
    }

    private void onEquals() {
        if (operator.isEmpty()) return;
        if (waitingForSecond) {
            showError("Incomplete expression");
            return;
        }
        double second;
        try {
            second = Double.parseDouble(displayField.getText());
        } catch (NumberFormatException e) {
            showError("Invalid input");
            return;
        }
        expressionLabel.setText(fmt(firstOperand) + " " + operator + " " + fmt(second) + " =");
        try {
            double result = calculator.evaluate(firstOperand, operator, second);
            displayField.setText(fmt(result));
        } catch (ArithmeticException e) {
            showError(e.getMessage());
            return;
        }
        operator = "";
        waitingForSecond = false;
        justCalculated = true;
    }

    private void onClear() {
        displayField.setText("0");
        expressionLabel.setText(" ");
        operator = "";
        firstOperand = 0;
        waitingForSecond = false;
        justCalculated = false;
        hasError = false;
    }

    private void onDelete() {
        if (waitingForSecond) return;
        String cur = displayField.getText();
        if (cur.length() <= 1 || (cur.length() == 2 && cur.startsWith("-"))) {
            displayField.setText("0");
        } else {
            displayField.setText(cur.substring(0, cur.length() - 1));
        }
    }

    private void onNegate() {
        try {
            double v = Double.parseDouble(displayField.getText());
            displayField.setText(fmt(-v));
        } catch (NumberFormatException ignored) {}
    }


    private void showError(String msg) {
        displayField.setText("Error: " + msg);
        expressionLabel.setText(" ");
        operator = "";
        waitingForSecond = false;
        hasError = true;
    }

    private String fmt(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v) && Math.abs(v) < 1e15) {
            return String.valueOf((long) v);
        }
        return String.valueOf(v);
    }
}