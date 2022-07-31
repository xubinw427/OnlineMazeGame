package ca.MazeOnline.Model.gameMechanics;


import ca.MazeOnline.Model.util.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Model {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private Cheese cheese = new Cheese(new Vector2D(0,0));;
    private Cat cat1, cat2, cat3;
    private Mouse mouse;
    private Maze maze = new Maze();
    private Cell[][] map;
    private int count = 0;
    private int goal = 5;

    public void setGoal(int goal) {
        this.goal = goal;
    }

    //Getters
    public Model()
    {
        this.formUp();
    }
    public Maze getMaze() {
        return maze;
    }
    public Cell[][] getMap() {
        return map;
    }
    public Cell getMapAt(int i, int j)
    {
        return this.map[i][j];
    }
    public Cheese getCheese() {
        return cheese;
    }
    public Cat getCat1() {
        return cat1;
    }
    public Cat getCat2() {
        return cat2;
    }
    public Cat getCat3() {
        return cat3;
    }
    public Mouse getMouse() {
        return mouse;
    }
    public int getCount() {
        return count;
    }
    public int getGoal() {
        return goal;
    }
    public Iterable<Cat> getCats()
    {
        List<Cat> cats = new ArrayList<>();
        cats.add(this.cat1);
        cats.add(this.cat2);
        cats.add(this.cat3);
        Iterable<Cat> returnCats = cats;
        return returnCats;
    }
    public boolean isGameWon()
    {
        return this.count >= this.goal;
    }
    public boolean isGameLost()
    {
        return this.mouseHasBeenEaten();
    }
    private void removeTheMist(Mouse mouse)
    {
        int row = mouse.getPositionRow();
        int column = mouse.getPositionColumn();
        for(int i = row - 1; i <= row + 1; i++)
        {
            for(int j = column - 1; j <= column + 1; j++)
            {
                map[i][j].makeVisible();
            }
        }
    }
    private void formUp()
    {

        this.maze.formMaze();
        this.map = new Cell[maze.getHeight()][maze.getWidth()];
        int height = maze.getHeight();
        int width = maze.getWidth();
        mouse = new Mouse(new Vector2D(1,1));
        cat1 = new Cat(new Vector2D(1, width - 2));
        cat2 = new Cat(new Vector2D(height - 2,1));
        cat3 = new Cat(new Vector2D(height - 2, width - 2));
        cheese.respawn(maze);
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                Vector2D sit = new Vector2D(i,j);
                map[i][j] = new Cell(sit);
                if(i == 0 || i == height - 1 || j == 0 || j == width - 1)
                    map[i][j].makeVisible();
                if(maze.getMazeAt(i,j))
                    map[i][j].setWallOrNot(true);
            }
        }
        this.removeTheMist(mouse);
    }
    //To decide which move should be doing next.
    private int moveOrCheat(String s)
    {
        switch (s)
        {
            case "W":
            case "w":
            case "A":
            case "a":
            case "S":
            case "s":
            case "D":
            case "d":
                return 1;
            case "C":
            case "c":
                return 3;
            case "M":
            case "m":
                return 2;
            case"?":
                return 4;
        }
        return 0;
    }
    //To check whether the given mouse can take the given move in this maze.
    public boolean canMoveOrNot(String c)
    {
        if(c.length() > 1)
            return true;
        switch (c)
        {
            case "w":
            case "W":
                return !maze.getMazeAt(mouse.getPositionRow() - 1, mouse.getPositionColumn());
            case "S":
            case "s":
                return !maze.getMazeAt(mouse.getPositionRow() + 1, mouse.getPositionColumn());
            case "A":
            case "a":
                return !maze.getMazeAt(mouse.getPositionRow(), mouse.getPositionColumn() - 1);
            case "D":
            case "d":
                return !maze.getMazeAt(mouse.getPositionRow(), mouse.getPositionColumn() + 1);
            default:
                return true;
        }
    }
    //To check whether this mouse is alive or not.
    public boolean mouseHasBeenEaten()
    {
        if(mouse.getPositionColumn() == cat1.getPositionColumn() && mouse.getPositionRow() == cat1.getPositionRow())
            return true;
        if(mouse.getPositionColumn() == cat2.getPositionColumn() && mouse.getPositionRow() == cat2.getPositionRow())
            return true;
        if(mouse.getPositionColumn() == cat3.getPositionColumn() && mouse.getPositionRow() == cat3.getPositionRow())
            return true;

        return false;
    }
    //The body function to run the game.
    public void playGame(String s)
    {
        switch (this.moveOrCheat(s))
        {
            case 1:
                mouse.randomChooseNextMove(s);
                this.removeTheMist(mouse);
                if(mouse.getPositionRow() == cheese.getPositionRow() && mouse.getPositionColumn() == cheese.getPositionColumn()) {
                    cheese.respawn(maze);
                    count = count + 1;
                }
//                cat1.randomChooseNextMove(maze);
//                cat2.randomChooseNextMove(maze);
//                cat3.randomChooseNextMove(maze);
                break;
            case 2:
                for(int i = 0; i < maze.getHeight(); i++)
                {
                    for(int j = 0; j < maze.getWidth(); j++)
                    {
                        map[i][j].makeVisible();
                    }
                }
                break;
            case 3:
                goal = 1;
                break;
            case 4:
                System.out.println("DIRECTIONS:\n" +
                        "\tFind 5 cheese before a cat eats you!\n" +
                        "LEGEND:\n" +
                        "\t#: Wall\n" +
                        "\t@: You (a mouse)\n" +
                        "\t!: Cat\n" +
                        "\t$: Cheese\n" +
                        "\t.: Unexplored space\n" +
                        "MOVES:\n" +
                        "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                        "\t(You must press enter after each move).");
                System.out.println(" ");
                break;



        }
    }

    public void moveCats()
    {
        cat1.randomChooseNextMove(maze);
        cat2.randomChooseNextMove(maze);
        cat3.randomChooseNextMove(maze);
    }
    //Used to debug.
    private void printMap()
    {
        for(int i = 0; i < maze.getHeight(); i++)
        {
            for(int j = 0; j < maze.getWidth(); j++)
            {
                if(map[i][j].isWallOrNot())
                {
//                    if(map[i][j].isVisible())
                    System.out.print(ANSI_BLUE + "# ");
//                    else
//                        System.out.print(". ");

                }
                else
                {
                    if(map[i][j].getPositionRow() == mouse.getPositionRow() && map[i][j].getPositionColumn() == mouse.getPositionColumn())
                    {
                        System.out.print(ANSI_YELLOW + "@ ");
                    }
                    else if(map[i][j].getPositionRow() == cheese.getPositionRow() && map[i][j].getPositionColumn() == cheese.getPositionColumn())
                    {
                        System.out.print(ANSI_PURPLE + "$ ");
                    }
                    else if((map[i][j].getPositionRow() == cat1.getPositionRow() && map[i][j].getPositionColumn() == cat1.getPositionColumn())
                            || (map[i][j].getPositionRow() == cat2.getPositionRow() && map[i][j].getPositionColumn() == cat2.getPositionColumn())
                            || (map[i][j].getPositionRow() == cat3.getPositionRow() && map[i][j].getPositionColumn() == cat3.getPositionColumn()))
                    {
                        System.out.print(ANSI_RED + "! ");
                    }
                    else
                    {
//                        if(model.getMap()[i][j].isVisible())
                        System.out.print("  ");
//                        else
//                            System.out.print(". ");
                    }
                }
            }

            System.out.println();
        }
        System.out.println("_______________________________________");
    }

}
