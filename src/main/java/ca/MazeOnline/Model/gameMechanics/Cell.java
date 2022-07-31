package ca.MazeOnline.Model.gameMechanics;

import ca.MazeOnline.Model.util.Vector2D;

public class Cell {
    private Vector2D position;
    private boolean visible;
    private boolean wallOrNot = false; // 0: wall; 1: road; 2: mouse; 3: cheese; 4: cat

    public Cell(Vector2D position) {
        this.position = position;
        visible = false;
    }
    public boolean isWallOrNot() {
        return wallOrNot;
    }

    public void setWallOrNot(boolean wallOrNot) {
        this.wallOrNot = wallOrNot;
    }

    public void setPositionRow(int i)
    {
        this.position.setRow(i);
    }
    public void setPositionColumn(int i)
    {
        this.position.setColumn(i);
    }
    public int getPositionRow()
    {
        return this.position.getRow();
    }
    public int getPositionColumn()
    {
        return this.position.getColumn();
    }
    public boolean isVisible(){return visible;}
    public void makeVisible(){
        visible = true;
    }
}