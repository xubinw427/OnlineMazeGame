package ca.MazeOnline.API;

import ca.MazeOnline.Model.gameMechanics.Model;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiGameWrapper makeFromGame(Model game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;

        // Populate this object!
        wrapper.numCheeseFound = game.getCount();
        wrapper.numCheeseGoal = game.getGoal();
        wrapper.isGameWon = game.isGameWon();
        wrapper.isGameLost = game.isGameLost();

        return wrapper;
    }
}
