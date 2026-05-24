package vn.oop;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;

public class DateModel {

    private LocalDate date;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public DateModel() {
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void shiftDays(int days) {
        LocalDate old = this.date;
        this.date = this.date.plusDays(days);
        support.firePropertyChange("date", old, this.date);
    }

    public void shiftMonths(int months) {
        LocalDate old = this.date;
        this.date = this.date.plusMonths(months);
        support.firePropertyChange("date", old, this.date);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
