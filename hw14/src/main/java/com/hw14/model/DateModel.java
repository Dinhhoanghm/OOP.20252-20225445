package com.hw14.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateModel {

    private final ObjectProperty<LocalDate> currentDate =
            new SimpleObjectProperty<>(LocalDate.now());
    private final StringProperty formatPattern =
            new SimpleStringProperty("dd/MM/yyyy");

    private final StringBinding formattedDate = Bindings.createStringBinding(
            () -> {
                LocalDate date = currentDate.get();
                String pattern = formatPattern.get();
                if (date == null || pattern == null || pattern.isBlank()) return "";
                return DateTimeFormatter.ofPattern(pattern).format(date);
            },
            currentDate, formatPattern
    );

    public StringBinding getFormattedDate() {
        return formattedDate;
    }

    public LocalDate computeDate(int amount, boolean isBefore, String unit) {
        LocalDate date = currentDate.get();
        if (date == null) return null;
        long delta = isBefore ? -amount : amount;
        try {
            return switch (unit) {
                case "Days"   -> date.plusDays(delta);
                case "Months" -> date.plusMonths(delta);
                case "Years"  -> date.plusYears(delta);
                default       -> date;
            };
        } catch (Exception e) {
            return null;
        }
    }

    public ObjectProperty<LocalDate> selectedDateProperty() {
        return currentDate;
    }

    public StringProperty dateFormatProperty() {
        return formatPattern;
    }

    public LocalDate getSelectedDate() {
        return currentDate.get();
    }

    public void setSelectedDate(LocalDate date) {
        currentDate.set(date);
    }

    public String getDateFormat() {
        return formatPattern.get();
    }

    public void setDateFormat(String format) {
        formatPattern.set(format);
    }
}
