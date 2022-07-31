package ca.MazeOnline.Model.userInterface;


import ca.MazeOnline.Model.gameMechanics.Model;

import java.util.Scanner;
enum Operations
{
    UP, DOWN, LEFT, RIGHT, CHEAT, CLEAR;
}

public class UI
{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private Model model = new Model();
    //To check whether the input is valid or not.
    public boolean ValidInput(String s)
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
                return model.canMoveOrNot(s);
            case "C":
            case "c":
            case "M":
            case "m":
            case"?":
                return true;
            default:
                return false;
        }
    }
    //To print the final map.
    public void printMap()
    {
        System.out.println("Maze:");
        for(int i = 0; i < model.getMaze().getHeight(); i++)
        {
            for(int j = 0; j < model.getMaze().getWidth(); j++)
            {
                if(model.getMap()[i][j].isWallOrNot())
                {
                    if(model.getMap()[i][j].isVisible())
                        System.out.print(ANSI_BLUE + "# ");
                    else
                        System.out.print(". ");

                }
                else
                {
                    if(model.getMap()[i][j].getPositionRow() == model.getMouse().getPositionRow() && model.getMap()[i][j].getPositionColumn() == model.getMouse().getPositionColumn())
                    {
                        if(model.mouseHasBeenEaten())
                            System.out.print(ANSI_YELLOW + "X ");
                        else
                            System.out.print(ANSI_YELLOW + "@ ");
                    }
                    else if(model.getMap()[i][j].getPositionRow() == model.getCheese().getPositionRow() && model.getMap()[i][j].getPositionColumn() == model.getCheese().getPositionColumn())
                    {
                        System.out.print(ANSI_PURPLE + "$ ");
                    }
                    else if((model.getMap()[i][j].getPositionRow() == model.getCat1().getPositionRow() && model.getMap()[i][j].getPositionColumn() == model.getCat1().getPositionColumn())
                            || (model.getMap()[i][j].getPositionRow() == model.getCat2().getPositionRow() && model.getMap()[i][j].getPositionColumn() == model.getCat2().getPositionColumn())
                            || (model.getMap()[i][j].getPositionRow() == model.getCat3().getPositionRow() && model.getMap()[i][j].getPositionColumn() == model.getCat3().getPositionColumn()))
                    {
                        System.out.print(ANSI_RED + "! ");
                    }
                    else
                    {
                        if(model.getMap()[i][j].isVisible())
                            System.out.print("  ");
                        else
                            System.out.print(". ");
                    }
                }
            }

            System.out.println();
        }
        System.out.println("_______________________________________");
    }
    //To take and pass input from user.
    public void interFace()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Xubin Wang\n" +
                "----------------------------------------\n" +
                "\n" +
                "DIRECTIONS:\n" +
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
        do
        {
            //this.model.getMaze().printMaze();
            this.printMap();

            System.out.println("Cheese collected: " + model.getCount() + " of " + model.getGoal());
            System.out.print("Enter your move [WASD?]: ");
            String operation = input.nextLine();
            while(!this.ValidInput(operation))
            {
                if(!model.canMoveOrNot(operation))
                {
                    System.out.println("Invalid move: you cannot move through walls!");
                }
                else
                {
                    System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
                }
                System.out.print("Enter your move [WASD?]: ");
                operation = input.nextLine();

            }
            model.playGame(operation);
        }while (model.getCount() < model.getGoal() && !model.mouseHasBeenEaten());

        if(model.mouseHasBeenEaten())
        {
            System.out.println("I'm sorry, you have been eaten!");
            System.out.println(" ");
            this.printMap();
            System.out.println("Cheese collected: " + model.getCount() + " of " + model.getGoal());
            System.out.println("GAME OVER; please try again.");
        }
        else
        {
            System.out.println("Congratulations! You won!");
            System.out.println(" ");
            this.printMap();
            System.out.println("Cheese collected: " + model.getCount() + " of " + model.getGoal());
        }
    }





}
