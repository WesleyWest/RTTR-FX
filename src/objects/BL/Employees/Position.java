package objects.BL.Employees;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.BL.SimpleObject;

public class Position extends SimpleObject{
    public Position(Integer id, String description, boolean isDeleted) {
        super(id, description, isDeleted);
    }

    public static ObservableList<SimpleObject<Position>> getSortedList(ObservableList<SimpleObject<Position>> incomingList){
        incomingList.sort((o1, o2) -> o2.getID()-o1.getID());

        return FXCollections.observableArrayList(incomingList);
    }
}
