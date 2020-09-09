package inventory;

import java.util.ArrayList;

public class InventoryGrid<T> {

    int xRows = 5;
    int yRows = 4;
    int size;
    int capacity = xRows * yRows;

    ArrayList<T> rowX;
    ArrayList<T> rowY;

    InventoryGrid() {
        rowX = new ArrayList<T>(xRows);
        rowY = new ArrayList<T>(yRows);
        size = 0;
    }

    public void add(T t) {
    }




}