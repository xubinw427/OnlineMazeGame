package ca.MazeOnline.Model.gameMechanics;


import ca.MazeOnline.Model.util.Vector2D;
public class Cheese extends Cell{

    public Cheese(Vector2D position) {
        super(position);
    }
    public void respawn(Maze m)
    {

        int height = m.getHeight();
        int width = m.getWidth();


        int randomRowInInt;
        int randomColumnInInt;
        do {
            double randomRow = (double)(height - 2) * Math.random() + 1;
            randomRowInInt = (int) randomRow;
            double randomColumn = (double)(width - 2) * Math.random() + 1;
            randomColumnInInt = (int) randomColumn;
            this.setPositionColumn(randomColumnInInt);
            this.setPositionRow(randomRowInInt);
        }while (m.getMazeAt(randomRowInInt,randomColumnInInt));
    }
    //Used to debug.
    public void printCheese()
    {
        System.out.println("This Cheese is at :" + getPositionRow() + " Row, " + getPositionColumn() + " Column");
    }
}
