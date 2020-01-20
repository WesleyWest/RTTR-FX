package objects.BL.Employees;

import objects.BL.SimpleObject;

public class Position extends SimpleObject implements Comparable {
    private int weight;

    public Position(Integer id, String description, int weight, boolean isDeleted) {
        super(id, description, isDeleted);
        this.weight=weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object o) {
        return ((Position) o).getWeight()-this.getWeight();
    }
}
