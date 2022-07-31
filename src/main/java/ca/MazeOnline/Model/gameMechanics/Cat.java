package ca.MazeOnline.Model.gameMechanics;

import ca.MazeOnline.Model.util.Vector2D;

public class Cat extends Cell {

    public Cat(Vector2D position) {
        super(position);
    }
    private boolean canMove(double random, Maze maze)
    {
        if(0 < random && random <= 0.25) // Move to right
        {
            return !maze.getMazeAt(this.getPositionRow(), this.getPositionColumn() + 1);
        }
        else if(0.25 < random && random <= 0.5) // Move to left
        {
            return !maze.getMazeAt(this.getPositionRow(), this.getPositionColumn() - 1);
        }
        else if(0.5 < random && random <= 0.75) // Move up
        {
            return !maze.getMazeAt(this.getPositionRow() - 1, this.getPositionColumn());
        }
        else                                     // Move down
        {
            return !maze.getMazeAt(this.getPositionRow() + 1, this.getPositionColumn());
        }
    }
    public void randomChooseNextMove(Maze maze)
    {
        double random;
        do {
            random = Math.random();
        }while(!this.canMove(random,maze));
        if(0 < random && random <= 0.25) // Move to right
        {
            this.setPositionColumn(this.getPositionColumn() + 1);
            //System.out.println("This Cat Move RIGHT.");
        }
        else if(0.25 < random && random <= 0.5) // Move to left
        {
            this.setPositionColumn(this.getPositionColumn() - 1);
            //System.out.println("This Cat Move LEFT.");
        }
        else if(0.5 < random && random <= 0.75) // Move up
        {
            this.setPositionRow(this.getPositionRow() - 1);
            //System.out.println("This Cat Move UP.");
        }
        else                                     // Move down
        {
            this.setPositionRow(this.getPositionRow() + 1);
            //System.out.println("This Cat Move DOWN.");
        }

    }
    //Used to debug.
    public void printCat()
    {
        System.out.println("This Cat is at :" + getPositionRow() + " Row, " + getPositionColumn() + " Column");
    }

}
