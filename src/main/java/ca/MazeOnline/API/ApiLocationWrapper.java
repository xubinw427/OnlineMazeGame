package ca.MazeOnline.API;

import ca.MazeOnline.Model.gameMechanics.Cat;
import ca.MazeOnline.Model.gameMechanics.Cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApiLocationWrapper {
    public int x;
    public int y;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiLocationWrapper makeFromCellLocation(Cell cell) {
        ApiLocationWrapper location = new ApiLocationWrapper();

        // Populate this object!
        location.x = cell.getPositionColumn();
        location.y = cell.getPositionRow();

        return location;
    }
    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static List<ApiLocationWrapper> makeFromCellLocations(Iterable<Cat> cells) {
        List<ApiLocationWrapper> locations = new ArrayList<>();

        // Populate this object!
        for(Cell cell: cells)
        {
            ApiLocationWrapper temp = ApiLocationWrapper.makeFromCellLocation(cell);
            locations.add(temp);
        }
        return locations;
    }
}