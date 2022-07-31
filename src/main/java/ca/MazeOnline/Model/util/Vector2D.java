package ca.MazeOnline.Model.util;

public class Vector2D {
    private int Row;
    private int Column;

    public Vector2D(int Row, int Column) {
        this.Row = Row;
        this.Column = Column;
    }

    public int getRow() {
        return Row;
    }

    public int getColumn() {
        return Column;
    }

    public void setRow(int Row) {
        this.Row = Row;
    }

    public void setColumn(int Column) {
        this.Column = Column;
    }

}
