package vn.oop;

import java.util.Scanner;

public class DoubleCalculator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Double number 1: ");
        double num1 = scanner.nextDouble();

        System.out.print("double number 2: ");
        double num2 = scanner.nextDouble();

        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;

        System.out.println("\nResults:");
        System.out.println("Sum result: " + sum);
        System.out.println("Difference result: " + difference);
        System.out.println("Product result: " + product);

        if (num2 != 0) {
            double quotient = num1 / num2;
            System.out.println("Quotient result: " + quotient);
        } else {
            System.out.println("Cannot divide by zero.");
        }

        scanner.close();
    }
}