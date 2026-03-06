package vn.oop.Homework02;

import java.util.Scanner;

public class DaysOfMonth {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int month = -1;
        int year = -1;

        while (month == -1) {
            System.out.print("Enter month: ");
            String m = sc.nextLine().toLowerCase();

            if (m.equals("1") || m.equals("jan") || m.equals("jan.") || m.equals("january"))
                month = 1;
            else if (m.equals("2") || m.equals("feb") || m.equals("feb.") || m.equals("february"))
                month = 2;
            else if (m.equals("3") || m.equals("mar") || m.equals("mar.") || m.equals("march"))
                month = 3;
            else if (m.equals("4") || m.equals("apr") || m.equals("apr.") || m.equals("april"))
                month = 4;
            else if (m.equals("5") || m.equals("may"))
                month = 5;
            else if (m.equals("6") || m.equals("jun") || m.equals("june"))
                month = 6;
            else if (m.equals("7") || m.equals("jul") || m.equals("july"))
                month = 7;
            else if (m.equals("8") || m.equals("aug") || m.equals("aug.") || m.equals("august"))
                month = 8;
            else if (m.equals("9") || m.equals("sep") || m.equals("sept.") || m.equals("september"))
                month = 9;
            else if (m.equals("10") || m.equals("oct") || m.equals("oct.") || m.equals("october"))
                month = 10;
            else if (m.equals("11") || m.equals("nov") || m.equals("nov.") || m.equals("november"))
                month = 11;
            else if (m.equals("12") || m.equals("dec") || m.equals("dec.") || m.equals("december"))
                month = 12;
            else
                System.out.println("Invalid month, enter again.");
        }

        while (year < 0) {
            System.out.print("Nhập năm: ");
            year = sc.nextInt();
            if (year < 0)
                System.out.println("Năm không thể nhỏ hơn 0, nhập lại: ");
        }

        boolean leapYear = false;

        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            leapYear = true;
        }

        int days = 0;

        if (month == 2) {
            if (leapYear)
                days = 29;
            else
                days = 28;
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
        }
        else {
            days = 31;
        }

        System.out.println(" Số ngày của tháng: " + days);

        sc.close();
    }
}