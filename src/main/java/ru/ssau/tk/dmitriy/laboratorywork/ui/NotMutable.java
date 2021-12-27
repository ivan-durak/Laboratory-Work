package ru.ssau.tk.dmitriy.laboratorywork.ui;
import java.util.List;
public class NotMutable extends Table {

    private static final long serialVersionUID = 4265684362156614538L;

    public NotMutable(List<String> xValues, List<String> yValues) {
        super(xValues, yValues);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}