package ca.MazeOnline.Model.gameMechanics;
// The following Algorithm is inspired by the idea found in the following cite https://en.wikipedia.org/wiki/Maze_generation_algorithm.
public class Maze {

    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private final int  width = 20;
    private final int  height = 15;



    private final boolean[][] maze = new boolean[height][width];

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    private void setMazeAt(int i, int j, boolean bool)
    {
        this.maze[i][j] = bool;
    }
    public boolean getMazeAt(int i, int j)
    {
        return this.maze[i][j];
    }
    public boolean[][] getWalls() {
        return maze;
    }
    public void printMaze()
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                //System.out.print(getMazeAt(i,j));
                if(getMazeAt(i,j))
                    System.out.print(ANSI_YELLOW +"# ");
                else
                    System.out.print(ANSI_BLUE + ". ");
            }
            System.out.println();
        }
        System.out.println("_______________________________________");
    }

    private void buildWall()
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if((i == 0 || i == height - 1) || (j == 0 || j == width - 1))
                {
                    //System.out.println("Now is filling " + i + " ," + j + " cell");
                    setMazeAt(i, j, true);
                }
                else
                {
                    setMazeAt(i, j, false);
                }

            }
        }
    }
    //To check whether we can horizontal cut the maze.
    private boolean canBeHorizontalCut(int rowX, int rowY, int columnX, int columnY)
    {
        int height = Math.abs(rowX - rowY) + 1;
        int width = Math.abs(columnX - columnY) + 1;
        return height > 4 && width > 3;
    }
    //To check whether we can vertical cut the maze.
    private boolean canBeVerticalCut(int rowX, int rowY, int columnX, int columnY)
    {
        int height = Math.abs(rowX - rowY) + 1;
        int width = Math.abs(columnX - columnY) + 1;
        return width > 4 && height > 3;
    }
    //To do horizontal cut.
    private int horizontalCut(int rowX, int rowY, int columnX, int columnY)
    {
        int height = Math.abs(rowX - rowY) + 1;
        int width = Math.abs(columnX - columnY) + 1;
        //System.out.println("Height is : " + height + " ," + "Width is : " + width + ".");

        double randomRow = (double)(height - 4) * Math.random() + (rowX + 2);
        //System.out.println("Cut the " + randomRow + " Row.");
        int randomRowInInt = (int) randomRow;
        //System.out.println("Cut the " + randomRowInInt + " Row.");

        int widthWithoutBlock = width - 2;
        int columnWithoutBlock = columnX + 1;
        //System.out.println("Before detecting block, our Length is: " + widthWithoutBlock + ", Start index is: " + columnWithoutBlock);
        if(!this.getMazeAt(randomRowInInt, columnX))
        {
            widthWithoutBlock = widthWithoutBlock - 1;
            columnWithoutBlock = columnWithoutBlock + 1;
        }
        if(!this.getMazeAt(randomRowInInt, columnY))
            widthWithoutBlock = widthWithoutBlock - 1;
        //System.out.println("After detecting, the Length is: " + widthWithoutBlock + ", Start index is: " + columnWithoutBlock);
        int lastindex = columnWithoutBlock + widthWithoutBlock;
        //System.out.println("Choose a Gap within [" + columnWithoutBlock+ ", " + lastindex + ")");
        int randomGapInInt = -1;
        if(widthWithoutBlock > 1)
        {
            double randomGap = (double) (widthWithoutBlock) * Math.random() + (columnWithoutBlock);
            randomGapInInt = (int) randomGap;
            //System.out.println("Open a Door at " + randomGapInInt + " Column.");
        }
        for(int j = columnWithoutBlock; j < lastindex; j++)
        {
            if(j != randomGapInInt)
                this.setMazeAt(randomRowInInt, j, true);
        }
        return randomRowInInt;

    }
    //To do vertical cut
    private int verticalCut(int rowX, int rowY, int columnX, int columnY)
    {
        int height = Math.abs(rowX - rowY) + 1;
        int width = Math.abs(columnX - columnY) + 1;
        //System.out.println("Height is : " + height + " ," + "Width is : " + width + ".");

        double randomColumn = (double)(width - 4) * Math.random() + (columnX + 2);
        //System.out.println("Cut the " + randomColumn + " Row.");
        int randomColumnInInt = (int) randomColumn;
        //System.out.println("Cut the " + randomColumnInInt + " Column.");

        int heightWithoutBlock = height - 2;
        int rowWithoutBlock = rowX + 1;
        //System.out.println("Before detecting block, our Length is: " + heightWithoutBlock + ", Start index is: " + rowWithoutBlock);
        if(!this.getMazeAt(rowX,randomColumnInInt))
        {
            heightWithoutBlock = heightWithoutBlock - 1;
            rowWithoutBlock = rowWithoutBlock + 1;
        }
        if(!this.getMazeAt(rowY, randomColumnInInt))
            heightWithoutBlock = heightWithoutBlock - 1;
        //System.out.println("After detecting, the Length is: " + heightWithoutBlock + ", Start index is: " + rowWithoutBlock);
        int lastindex = rowWithoutBlock + heightWithoutBlock;
        //System.out.println("Choose a Gap within [" + rowWithoutBlock + ", " + lastindex + ")");
        int randomGapInInt = -1;
        if(heightWithoutBlock > 1)
        {
            double randomGap = (double)(heightWithoutBlock) * Math.random() + (rowWithoutBlock);
            randomGapInInt = (int) randomGap;
            //System.out.println("Open a Door at " + randomGapInInt + " Row.");
        }


        for(int i = rowWithoutBlock; i < lastindex; i++)
        {
            if(i != randomGapInInt)
                this.setMazeAt(i, randomColumnInInt, true);
        }
        return randomColumnInInt;

    }
    //To Do recursive division.
    private void RecursiveDivision(int rowX, int rowY, int columnX, int columnY)
    {
        int height = this.getHeight();
        int width = this.getWidth();
        if(!canBeHorizontalCut(rowX, rowY, columnX, columnY) && !canBeVerticalCut(rowX, rowY, columnX, columnY))
        {
            //System.out.println("Cannot be Divided Anymore.");
            return;
        }
        else
        {
            double chooseNextCut ;
            if(!canBeHorizontalCut(rowX, rowY, columnX, columnY))
                chooseNextCut = 0.75;
            else if(!canBeVerticalCut(rowX, rowY, columnX, columnY))
                chooseNextCut = 0.25;
            else
                chooseNextCut = Math.random();

            if(chooseNextCut < 0.5)
            {
                //System.out.println("Now Processing Horizontal Cut.");
                int choosenRow = horizontalCut(rowX, rowY, columnX, columnY);
                //this.printMaze();
                this.RecursiveDivision(rowX, choosenRow, columnX, columnY);
                this.RecursiveDivision(choosenRow, rowY, columnX, columnY);
            }
            else
            {
                //System.out.println("Now Processing Vertical Cut.");
                int choosenColumn = verticalCut(rowX, rowY, columnX, columnY);
                //this.printMaze();
                this.RecursiveDivision(rowX, rowY, columnX, choosenColumn);
                this.RecursiveDivision(rowX, rowY, choosenColumn, columnY);
            }
        }
    }
    //To randomly add some loops.
    private void plusLoop()
    {
        for(int i = 1; i < height - 1; i++)
        {
            for(int j = 1; j < width - 1; j++)
            {
                if(getMazeAt(i,j))
                {
                    double probability = Math.random();
                    if(probability <= 0.1)
                        this.setMazeAt(i,j,false);
                }
            }
        }

    }
    //To check if a wall is put at the given vector, whether it will form a square or block the road.
    private boolean checkWallSquareOrBlock(int i, int j)
    {
        if((getMazeAt(i,j-1) & getMazeAt(i-1, j-1) & getMazeAt(i-1,j))
                || (getMazeAt(i-1,j) & getMazeAt(i-1,j+1) & getMazeAt(i, j+1))
                || (getMazeAt(i, j+1) & getMazeAt(i+1,j+1) & getMazeAt(i+1, j))
                || (getMazeAt(i+1, j) & getMazeAt(i+1, j-1) & getMazeAt(i, j-1)))
            return true;
        else if((getMazeAt(i-1, j-1) & !getMazeAt(i-1, j) & getMazeAt(i-1, j+1))
                || (getMazeAt(i-1, j+1) & !getMazeAt(i, j+1) & getMazeAt(i+1, j+1))
                || (getMazeAt(i+1,j+1) & !getMazeAt(i+1,j) & getMazeAt(i+1, j-1))
                || (getMazeAt(i+1, j-1) & !getMazeAt(i, j-1) & getMazeAt(i-1, j-1)))
            return true;
        else if((getMazeAt(i, j-1) & (getMazeAt(i-1, j) | getMazeAt(i-1, j+1)))
                || (getMazeAt(i, j+1) & (getMazeAt(i-1, j) | getMazeAt(i-1, j-1)))
                || (getMazeAt(i,j-1) & (getMazeAt(i+1,j) | getMazeAt(i+1, j+1)))
                || (getMazeAt(i, j+1) & (!getMazeAt(i+1, j) & getMazeAt(i+1, j-1))))
        {
            return true;
        }
        else if((getMazeAt(i+1, j-1) & (getMazeAt(i-1, j) | getMazeAt(i-1, j+1)))
                || (getMazeAt(i+1, j+1) & (getMazeAt(i-1, j) | getMazeAt(i-1, j-1)))
                || (getMazeAt(i-1,j-1) & (getMazeAt(i+1,j) | getMazeAt(i+1, j+1)))
                || (getMazeAt(i-1, j+1) & (!getMazeAt(i+1, j) & getMazeAt(i+1, j-1))))
        {
            return true;
        }
        else
            return false;
    }
    //To check whether there are empty squares square at the given vector.
    private boolean thereIsEmptySquare(int i, int j)
    {
        if (!getMazeAt(i, j))
        {
            return (!getMazeAt(i, j - 1) & !getMazeAt(i - 1, j - 1) & !getMazeAt(i - 1, j))
                    || (!getMazeAt(i - 1, j) & !getMazeAt(i - 1, j + 1) & !getMazeAt(i, j + 1))
                    || (!getMazeAt(i, j + 1) & !getMazeAt(i + 1, j + 1) & !getMazeAt(i + 1, j))
                    || (!getMazeAt(i + 1, j) & !getMazeAt(i + 1, j - 1) & !getMazeAt(i, j - 1));
        }
        return false;
    }
    //If there is empty square, put walls by some constrains.
    private void eliminateEmptySquares()
    {
        for(int i = 2; i < height - 2; i++)
        {
            for(int j = 2; j < width - 2; j++)
            {
                if(thereIsEmptySquare(i,j))
                {
                    if (!checkWallSquareOrBlock(i,j))
                        this.setMazeAt(i,j,true);
                }
            }
        }
        for(int k = 2; k < height - 2; k++)
        {
            if (thereIsEmptySquare(k, 1))
                if (!checkWallSquareOrBlock(k, 1))
                    this.setMazeAt(k, 1, true);
            if (thereIsEmptySquare(k, width - 2))
                if (!checkWallSquareOrBlock(k, width - 2))
                    this.setMazeAt(k, width - 2, true);
        }
        for(int m = 2; m < width - 2; m++)
        {
            if(thereIsEmptySquare(1,m))
                if (!checkWallSquareOrBlock(1,m))
                    this.setMazeAt(1,m,true);
            if (thereIsEmptySquare(height - 2,m))
                if (!checkWallSquareOrBlock(height - 2,m))
                    this.setMazeAt(height - 2,m,true);
        }


    }
    //To check whether there is some square.
    private boolean thereIsSquare()
    {
        for(int i = 2; i < height - 2; i++)
        {
            for(int j = 2; j < width - 2; j++)
            {
                if(((getMazeAt(i-1,j-1) == getMazeAt(i,j-1)) && (getMazeAt(i,j-1) == getMazeAt(i-1,j)) && (getMazeAt(i-1,j) == getMazeAt(i,j)) && (getMazeAt(i,j) == getMazeAt(i-1,j-1)))
                        || ((getMazeAt(i-1,j+1) == getMazeAt(i,j+1)) && (getMazeAt(i,j+1) == getMazeAt(i-1,j)) && (getMazeAt(i-1,j) == getMazeAt(i,j)) && (getMazeAt(i,j) == getMazeAt(i-1,j+1)))
                        || ((getMazeAt(i+1,j) == getMazeAt(i,j+1)) && (getMazeAt(i,j+1) == getMazeAt(i+1,j+1)) && (getMazeAt(i+1,j+1) == getMazeAt(i,j)) && (getMazeAt(i,j) == getMazeAt(i+1,j)))
                        || ((getMazeAt(i+1,j) == getMazeAt(i+1,j-1)) && (getMazeAt(i+1,j-1) == getMazeAt(i,j-1)) && (getMazeAt(i,j-1) == getMazeAt(i,j)) && (getMazeAt(i,j) == getMazeAt(i+1,j))))
                {
                    //System.out.println("There is a square at : " + i + ", " + j);
                    return true;
                }

            }
        }
        return false;
    }
    //To build up a perfect maze.
    public void formMaze()
    {
        do
        {
            this.buildWall();
            this.RecursiveDivision(0,getHeight() - 1,0,getWidth() - 1);
            //this.printMaze();
            this.thereIsSquare();
            this.plusLoop();
            //this.printMaze();
            this.eliminateEmptySquares();
        }while (this.thereIsSquare());
    }
//    public static void main(String[] args)
//    {
//        Maze m = new Maze();
//        m.formMaze();
//        m.printMaze();
//        if(m.thereIsSquare())
//            System.out.println("There is squares.");
//    }
}