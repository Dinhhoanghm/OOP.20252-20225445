package vn.oop;

public class Calculator {

    public double evaluate(double a, String operator, double b) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a / b;
            }
            case "%" -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a % b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}
