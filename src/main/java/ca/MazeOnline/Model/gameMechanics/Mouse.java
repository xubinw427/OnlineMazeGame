package ca.MazeOnline.Model.gameMechanics;

import ca.MazeOnline.Model.util.Vector2D;

public class Mouse extends Cell {
    public Mouse(Vector2D position) {
        super(position);
    }


    public void randomChooseNextMove(String c)
    {
        switch (c)
        {
            case "W":
            case "w":
                this.setPositionRow(this.getPositionRow() - 1);
                break;
            case "S":
            case "s":
                this.setPositionRow(this.getPositionRow() + 1);
                break;
            case "A":
            case "a":
                this.setPositionColumn(this.getPositionColumn() - 1);
                break;
            default:
                this.setPositionColumn(this.getPositionColumn() + 1);
                break;
        }
    }
    //Used to debug
    public void printMouse()
    {
        System.out.println("This Mouse is at :" + getPositionRow() + " Row, " + getPositionColumn() + " Column");
    }
}
