import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
    private Timer t;
    private int mouseBufferX;
    private int mouseBufferY;

    private int gridCenterX;
    private int gridCenterY;

    int timeStep = 1000;

    public NewLifeCanvas(LifeGridCells g) {
        grid = g;
        setContentPane(MainPanel);
        setTitle("Game of Life");
        System.out.println(MainPanel.getInsets().top + getInsets().bottom);
        setSize(20*grid.getNumCols() + 18, 20*grid.getNumRows() + 105);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        GamePanel.add(new Grid());
        gridCenterX = grid.getNumCols()/2;
        gridCenterY = grid.getNumRows()/2;
        t = new Timer(timeStep, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });
        GamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX()/20;
                int y = e.getY()/20;
                grid.toggleCell(x,y);
                GamePanel.repaint();
            }
        });
        GamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if(e.getX() != mouseBufferX || e.getY() != mouseBufferY){
                    int x = e.getX()/20;
                    int y = e.getY()/20;
                    grid.toggleCell(x,y);
                    GamePanel.repaint();
                    mouseBufferX = e.getX();
                    mouseBufferY = e.getY();
                }


            }
        });
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        Stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });


        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setSpeed(-1*slider1.getValue());
                System.out.println(slider1.getValue());
            }
        });
        Fill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (comboBox1.getSelectedIndex()){
                    case 0:
                        clear();
                        break;
                    case 1:
                        clear();
                        fillRandom();
                        break;
                    case 2:
                        clear();
                        addBlock();
                        break;
                    case 3:
                        clear();
                        addBeehive();
                        break;
                    case 4:
                        clear();
                        addLoaf();
                        break;
                    case 5:
                        clear();
                        addBoat();
                        break;
                    case 6:
                        clear();
                        addTub();
                        break;
                    case 7:
                        clear();
                        addBlinker();
                        break;
                    case 8:
                        clear();
                        addToad();
                        break;
                    case 9:
                        clear();
                        addBeacon();
                        break;
                    case 10:
                        clear();
                        addPulsar();
                        break;
                }
            }
        });
    }

    public void setSpeed(int millisecs) {
        boolean isRunning = t.isRunning();
        if(millisecs == 3000){
            t.stop();
            Start.setText("Next");
            return;
        }
        else if (Start.getText().equals("Next")){
            Start.setText("Start");
        }
        timeStep = millisecs;
        t.setDelay(timeStep);
        if(isRunning) t.restart();
    }

    public void stop()
    {
        t.stop();
    }

    public void start()
    {
        System.out.println(Start.getText());
        if (Start.getText().equals("Next")){
            next();
            return;
        }
        else t.start();
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

    }

    public void clear(){
        grid.clear();
        GamePanel.repaint();
    }

    public void fillRandom(){
        grid.fillRandom();
        GamePanel.repaint();
    }

    public void addBlock(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 1, true);
    }

    public void addBeehive(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 1, true);
    }

    public void addLoaf(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 3, gridCenterY + 1, true);
    }

    public void addBoat(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX +1 , gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 2, gridCenterY  -1, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 2, true);
    }

    public void addTub(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
    }

    public void addBlinker(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX, gridCenterY + 2, true);
    }

    public void addToad(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 1, true);
    }

    public void addBeacon(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 3, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 3, gridCenterY + 3, true);
    }

    public void addPulsar() {
        grid.setCell(gridCenterX - 2, gridCenterY - 4, true);
        grid.setCell(gridCenterX - 2, gridCenterY - 3, true);
        grid.setCell(gridCenterX - 2, gridCenterY - 2, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 3, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 4, true);

        grid.setCell(gridCenterX - 4, gridCenterY - 2, true);
        grid.setCell(gridCenterX - 3, gridCenterY - 2, true);
        grid.setCell(gridCenterX - 2, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 3, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 4, gridCenterY - 2, true);

        grid.setCell(gridCenterX - 4, gridCenterY + 2, true);
        grid.setCell(gridCenterX - 3, gridCenterY + 2, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 3, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 4, gridCenterY + 2, true);

        grid.setCell(gridCenterX + 2, gridCenterY - 4, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 3, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 4, true);

        grid.setCell(gridCenterX - 4, gridCenterY - 4, true);

    }
}
