import java.awt.*;
/**
 * HONOR PLEDGE: All work here is honestly obtained and is my own.  Signed:  James Kester
 * @author kesterJ
 * Date of Completion: 1/12/2024
 * Assignment:     Game of Life
 *
 * Attribution:  Original Game of Life GUI
 *
 * General Description:   this is the class that holds the grid of cells
 *
 * Advanced:  	N/A
 *
 * Errata:  N/A
 *
 */

public class LifeGridCells {
    private int width;
    private int height;
    private Cell[][] grid;
    private Cell[][] lastGrid;

    /**
     * Constructor for the grid of cells
     * @param width
     * @param height
     */
    public LifeGridCells(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * Sets the cell at the given coordinates to the given state
     * @param x
     * @param y
     * @param alive
     */
    public void setCell(int x, int y, boolean alive) {
        if (alive) {grid[x][y].revive(); grid[x][y].hasBeenAlive();}
        else grid[x][y].kill();

    }

    /**
     * Returns the number of columns in the grid
     * @return
     */
    public int getNumCols() {
        return width;
    }

    /**
     * Returns the number of rows in the grid
     * @return
     */
    public int getNumRows() {
        return height;
    }

    /**
     * Evolves the grid of cells to the next generation
     */
    public void evolve(){
        lastGrid = grid;
        Cell[][] newGrid = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                newGrid[i][j] = new Cell();
            }
        }
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                int neighbors = countNeighbors(x, y);
                if (neighbors == 3) {
                    newGrid[x][y].alive = true;
                    newGrid[x][y].setAge(grid[x][y].getAge());
                    newGrid[x][y].hasBeenAlive = grid[x][y].hasBeenAlive;
                }
                else {newGrid[x][y].alive = neighbors == 2 && grid[x][y].getState(); newGrid[x][y].setAge(grid[x][y].getAge()); newGrid[x][y].hasBeenAlive = grid[x][y].hasBeenAlive;}
            }
        }
        grid = newGrid;
        for (int y=0; y<height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y].getState()){
                    grid[x][y].hasBeenAlive();
                }
                if(grid[x][y].getState() && lastGrid[x][y].getState()){
                    grid[x][y].ageUp();
                }
                else if(grid[x][y].getState() && !lastGrid[x][y].getState()){
                    grid[x][y].setAge(0);
                }
            }
        }
    }
    /**
     * Returns the number of neighbors of the cell at the given coordinates
     * @param x
     * @param y
     * @return
     */
    public int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if ((i >= 0 && i < width) && (j >= 0 && j < height)){
                    if(grid[i][j].getState()){
                        count++;
                    }
                }
            }
        }
        if(grid[x][y].getState())
            count--;
        return count;
    }

    /**
     * Returns the cell at the given coordinates
     * @param x
     * @param y
     * @return
     */
    public void toggleCell(int x, int y){
        if(x < 0 || x >= width || y < 0 || y >= height){
            return;
        }
        if(grid[x][y].getState()){
            grid[x][y].kill();
        }
        else{
            grid[x][y].revive();
        }
    }
    /**
    Returns the grid of cells
     * @return
     */

    public Cell[][] getGrid(){
        return grid;
    }

    /**
     * Clears the grid
     */
    public void clear() {
        for (int y=0; y<height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y].kill();
                grid[x][y].resetAge();
                grid[x][y].resetHasBeenAlive();

            }
        }
    }

    /**
     * Fills the grid with random cells
     */
    public void fillRandom() {
        for (int y=0; y<height; y++) {
            for (int x = 0; x < width; x++) {
                if(Math.random() < 0.5){
                    grid[x][y].kill();
                }
                else{
                    grid[x][y].revive();
                }
            }
        }
    }
}
