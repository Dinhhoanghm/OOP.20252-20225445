package com.hw14.controller;

import com.hw14.model.DateModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateController {

    private final DateModel model = new DateModel();

    @FXML private Label dateLabel;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> formatBox;
    @FXML private TextField inputField;
    @FXML private RadioButton beforeRadio;
    @FXML private RadioButton afterRadio;
    @FXML private ToggleGroup directionGroup;
    @FXML private RadioButton daysRadio;
    @FXML private RadioButton monthsRadio;
    @FXML private RadioButton yearsRadio;
    @FXML private ToggleGroup unitGroup;
    @FXML private Button calculateButton;
    @FXML private Label outputLabel;
    @FXML private Label validationLabel;

    @FXML
    public void initialize() {
        formatBox.getItems().addAll(
                "dd/MM/yyyy",
                "MM/dd/yyyy",
                "dd.MM.yyyy",
                "yyyy-MM-dd",
                "dd-MM-yyyy",
                "dd MMM yyyy"
        );
        formatBox.setValue("dd/MM/yyyy");

        datePicker.setValue(LocalDate.now());


        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) model.setSelectedDate(newVal);
        });

        formatBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) model.setDateFormat(newVal);
        });

        dateLabel.textProperty().bind(model.getFormattedDate());

        BooleanBinding inputInvalid = Bindings.createBooleanBinding(
                () -> {
                    String text = inputField.getText();
                    if (text == null || text.isBlank()) return true;
                    try {
                        return Integer.parseInt(text.trim()) <= 0;
                    } catch (NumberFormatException e) {
                        return true;
                    }
                },
                inputField.textProperty()
        );
        calculateButton.disableProperty().bind(inputInvalid);

        inputField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                validationLabel.setText("");
                return;
            }
            try {
                int v = Integer.parseInt(newVal.trim());
                validationLabel.setText(v <= 0 ? "Amount must be a positive integer." : "");
            } catch (NumberFormatException e) {
                validationLabel.setText("Invalid input: please enter a positive integer.");
            }
        });

        beforeRadio.setSelected(true);
        daysRadio.setSelected(true);
    }

    @FXML
    public void handleCalculate() {
        int amount;
        try {
            amount = Integer.parseInt(inputField.getText().trim());
        } catch (NumberFormatException e) {
            validationLabel.setText("Invalid input: please enter a positive integer.");
            return;
        }

        boolean isBefore = beforeRadio.isSelected();
        String unit = ((RadioButton) unitGroup.getSelectedToggle()).getText();

        LocalDate result = model.computeDate(amount, isBefore, unit);
        if (result == null) {
            validationLabel.setText("Date calculation produced an out-of-range result.");
            outputLabel.setText("");
            return;
        }

        String formatted = DateTimeFormatter.ofPattern(model.getDateFormat()).format(result);
        outputLabel.setText("Result: " + formatted);
        validationLabel.setText("");
    }
}
