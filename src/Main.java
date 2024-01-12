public class Main {
    public static void main(String[] args) {
        LifeGridCells g = new LifeGridCells(5,5);
        g.setCell(1,1, true);
        g.setCell(1,2, true);
        g.setCell(2,1, true);
        g.setCell(2,2, true);

    }
}