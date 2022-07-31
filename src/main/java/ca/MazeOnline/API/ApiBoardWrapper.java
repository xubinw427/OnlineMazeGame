package ca.MazeOnline.API;

import ca.MazeOnline.Model.gameMechanics.Model;

import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiBoardWrapper makeFromGame(Model game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();

        // Populate this object!
        wrapper.boardWidth = game.getMaze().getWidth();
        wrapper.boardHeight = game.getMaze().getHeight();
        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(game.getMouse());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(game.getCheese());
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(game.getCats());
        wrapper.hasWalls = game.getMaze().getWalls();
        wrapper.isVisible = new boolean[wrapper.boardHeight][wrapper.boardWidth];
        for(int i = 0; i < wrapper.boardHeight; i++)
        {
            for(int j = 0; j < wrapper.boardWidth; j++)
            {
                wrapper.isVisible[i][j] = game.getMapAt(i,j).isVisible();
            }
        }
        return wrapper;
    }
}
