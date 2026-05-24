package vn.oop;

import java.time.DateTimeException;

public class DateController {

    private static final int MAX_VALUE = 36500;

    private final DateModel model;
    private final DateView view;

    public DateController(DateModel model, DateView view) {
        this.model = model;
        this.view  = view;

        model.addPropertyChangeListener(e -> view.updateDate(model.getDate()));

        view.addDaysBeforeListener(e   -> shift(Unit.DAY,   -1));
        view.addDaysAfterListener(e    -> shift(Unit.DAY,    1));
        view.addMonthsBeforeListener(e -> shift(Unit.MONTH, -1));
        view.addMonthsAfterListener(e  -> shift(Unit.MONTH,  1));

        view.updateDate(model.getDate());
    }

    private enum Unit { DAY, MONTH }

    private void shift(Unit unit, int direction) {
        view.clearError();

        String text = view.getInputText();
        if (text.isEmpty()) {
            view.showError("Vui lòng nhập một số.");
            return;
        }

        int value;
        try {
            value = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            view.showError("Giá trị không hợp lệ. Vui lòng nhập số nguyên dương.");
            return;
        }

        if (value < 0) {
            view.showError("Số phải là số dương (>= 0).");
            return;
        }
        if (value > MAX_VALUE) {
            view.showError("Số quá lớn (tối đa " + MAX_VALUE + ").");
            return;
        }

        int amount = direction * value;
        try {
            if (unit == Unit.DAY) model.shiftDays(amount);
            else                  model.shiftMonths(amount);
        } catch (DateTimeException e) {
            view.showError("Ngày kết quả nằm ngoài phạm vi hợp lệ.");
        }
    }
}
