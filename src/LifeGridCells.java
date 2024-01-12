public class LifeGridCells {
    private int width;
    private int height;
    private Cell[][] grid;
    private Cell[][] lastGrid;

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

    public void setCell(int x, int y, boolean alive) {
        if (alive) grid[x][y].revive();
        else grid[x][y].kill();
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public int getNumCols() {
        return width;
    }

    public int getNumRows() {
        return height;
    }

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
                }
                else newGrid[x][y].alive = neighbors == 2 && grid[x][y].getState();
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
            }
        }
        System.out.println("Evolved");
    }

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

    public Cell[][] getGrid(){
        return grid;
    }
}
