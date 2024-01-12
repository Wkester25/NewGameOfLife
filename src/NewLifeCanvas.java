import javax.swing.*;
import java.awt.*;

public class NewLifeCanvas extends JFrame {

    private JPanel MainPanel;
    private JPanel ContentPanel;
    private JPanel InputPanel;
    private JButton Start;
    private JSlider slider1;
    private JButton Stop;
    private JButton Fill;
    private JComboBox comboBox1;
    private JLabel Title;
    private LifeGridCells grid;
    private JPanel GamePanel;

    public NewLifeCanvas(LifeGridCells g) {
        grid = g;
        setContentPane(MainPanel);
        setTitle("Game of Life");
        System.out.println(MainPanel.getInsets().top + getInsets().bottom);
        setSize(20*grid.getNumCols() + 18, 20*grid.getNumRows() + 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        GamePanel.add(new Grid());
    }

    class Grid extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = grid.getNumCols();
            int height = grid.getNumRows();
            g.setColor(Color.red);
            int cellSize = 20;
            for (int x = 1; x <= width; x++) {
                g.drawLine(x * cellSize - 1, 0, x * cellSize - 1, cellSize * height - 1);
            }
            for (int y = 1; y <= height; y++) {
                g.drawLine( 0, y * cellSize - 1, cellSize * width - 1, y * cellSize - 1);
            }
            // draw populated cells

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid.getGrid()[x][y].getHasBeenAlive()){
                        System.out.println("Has been alive");
                    }
                }
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid.getGrid()[x][y].getState()){
                        g.setColor(grid.getGrid()[x][y].getCellColor());
                        g.fillRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
                    }
                }
            }

            // draw usedcells

        }
        public Dimension getPreferredSize() {
            return new Dimension(20*grid.getNumCols(), 20*grid.getNumRows());
        }
    }

    public void next(){
        grid.evolve();
        GamePanel.repaint();

    }



    public static void main(String[] args) throws InterruptedException {
        LifeGridCells g = new LifeGridCells(25,25);
        g.setCell(10,10, true);
        g.setCell(10,11, true);
        g.setCell(11,10, true);
        g.setCell(11,11, true);
        g.setCell(12,10, true);
        NewLifeCanvas window = new NewLifeCanvas(g);
        Thread.sleep(1000);
        window.next();
        Thread.sleep(1000);
        window.next();
        Thread.sleep(1000);
        window.next();
        Thread.sleep(1000);
        window.next();







    }


}
