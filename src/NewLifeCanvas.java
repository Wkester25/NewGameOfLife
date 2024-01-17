import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
/**
 * HONOR PLEDGE: All work here is honestly obtained and is my own.  Signed:  James Kester
 * @author kesterJ
 * Date of Completion: 1/12/2024
 * Assignment:     Game of Life
 *
 * Attribution:  Original Game of Life GUI
 *
 * General Description:   this class is the GUI for the game of life
 *
 * Advanced:  	JFrame GUI, JPanels, JButtons, JSliders, JComboBoxes, MouseListeners, MouseMotionListeners, Timers
 *
 * Errata:  display possibly has a bug where the grid is mirrored on the x axis
 *
 */

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
    private final LifeGridCells grid;
    private JPanel GamePanel;
    private JLabel Output;
    private final Timer t;
    private int mouseBufferX;
    private int mouseBufferY;

    private final int gridCenterX;
    private final int gridCenterY;

    int timeStep = slider1.getValue() * -1;

    /**
     * This is the constructor for the GUI
     * @param g this is the grid of cells that the GUI will display
     */
    public NewLifeCanvas(LifeGridCells g) {
        grid = g;
        setContentPane(MainPanel);
        setTitle("Game of Life");
        System.out.println(MainPanel.getInsets().top + getInsets().bottom);
        setSize(20*grid.getNumCols() + 18, 20*grid.getNumRows() + 105);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Output.setText("Stopped");
        setVisible(true);
        GamePanel.add(new Grid());
        gridCenterX = grid.getNumCols()/2;
        gridCenterY = grid.getNumRows()/2;
        t = new Timer(timeStep, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Output.setText("Running | Speed: " + (1000-timeStep) / 10d + "%");
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

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        Stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Output.setText("Stopped");
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
                    case 11:
                        clear();
                        addPentaDecathlon();
                        break;
                    case 12:
                        clear();
                        addGlider();
                        break;
                    case 13:
                        clear();
                        addLightWeightSpaceship();
                        break;
                    case 14:
                        clear();
                        addMiddleWeightSpaceship();
                        break;
                    case 15:
                        clear();
                        addHeavyWeightSpaceship();
                        break;
                }
            }
        });
        GamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int x = e.getX()/20;
                int y = e.getY()/20;
                if (x != mouseBufferX || y != mouseBufferY){
                    grid.toggleCell(x,y);
                    GamePanel.repaint();
                    mouseBufferX = x;
                    mouseBufferY = y;
                }
            }
        });
    }

    /**
     * This method sets the speed of the timer
     * @param millisecs
     */
    public void setSpeed(int millisecs) {
        boolean isRunning = t.isRunning();
        if(millisecs == 1000){
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

    /**
     * This method stops the timer
     */
    public void stop()
    {
        t.stop();
    }

    /**
     * This method starts the timer
     */
    public void start() {
        if (Start.getText().equals("Next")){
            next();
        }
        else t.start();
    }

    /**
     * This is the class that draws the grid
     */
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
            // draw usedCells
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid.getGrid()[x][y].getHasBeenAlive()){
                        System.out.println("hasBeenAlive");
                        g.setColor(Color.getHSBColor(.43f, .2f, 1f));
                        g.fillRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
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

    }
    /**
     * This method advances the grid one generation
     */
    public void next(){
        grid.evolve();
        GamePanel.repaint();

    }


    /**
     * These method apply patterns to the grid
     */
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
        grid.setCell(gridCenterX +2, gridCenterY + 1, true);
        grid.setCell(gridCenterX +3, gridCenterY + 1, true);
        grid.setCell(gridCenterX +4, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 4, true);
        grid.setCell(gridCenterX + 6, gridCenterY + 2, true);
        grid.setCell(gridCenterX + 6, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 6, gridCenterY + 4, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 6, true);
        grid.setCell(gridCenterX + 3, gridCenterY + 6, true);
        grid.setCell(gridCenterX + 4, gridCenterY + 6, true);

        grid.setCell(gridCenterX - 2, gridCenterY + 1, true);
        grid.setCell(gridCenterX - 3, gridCenterY + 1, true);
        grid.setCell(gridCenterX - 4, gridCenterY + 1, true);
        grid.setCell(gridCenterX - 1, gridCenterY + 2, true);
        grid.setCell(gridCenterX - 1, gridCenterY + 3, true);
        grid.setCell(gridCenterX - 1, gridCenterY + 4, true);
        grid.setCell(gridCenterX - 6, gridCenterY + 2, true);
        grid.setCell(gridCenterX - 6, gridCenterY + 3, true);
        grid.setCell(gridCenterX - 6, gridCenterY + 4, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 6, true);
        grid.setCell(gridCenterX - 3, gridCenterY + 6, true);
        grid.setCell(gridCenterX - 4, gridCenterY + 6, true);

        grid.setCell(gridCenterX +2, gridCenterY - 1, true);
        grid.setCell(gridCenterX +3, gridCenterY - 1, true);
        grid.setCell(gridCenterX +4, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 3, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 4, true);
        grid.setCell(gridCenterX + 6, gridCenterY - 2, true);
        grid.setCell(gridCenterX + 6, gridCenterY - 3, true);
        grid.setCell(gridCenterX + 6, gridCenterY - 4, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 6, true);
        grid.setCell(gridCenterX + 3, gridCenterY - 6, true);
        grid.setCell(gridCenterX + 4, gridCenterY - 6, true);

        grid.setCell(gridCenterX - 2, gridCenterY - 1, true);
        grid.setCell(gridCenterX - 3, gridCenterY - 1, true);
        grid.setCell(gridCenterX - 4, gridCenterY - 1, true);
        grid.setCell(gridCenterX - 1, gridCenterY - 2, true);
        grid.setCell(gridCenterX - 1, gridCenterY - 3, true);
        grid.setCell(gridCenterX - 1, gridCenterY - 4, true);
        grid.setCell(gridCenterX - 6, gridCenterY - 2, true);
        grid.setCell(gridCenterX - 6, gridCenterY - 3, true);
        grid.setCell(gridCenterX - 6, gridCenterY - 4, true);
        grid.setCell(gridCenterX - 2, gridCenterY - 6, true);
        grid.setCell(gridCenterX - 3, gridCenterY - 6, true);
        grid.setCell(gridCenterX - 4, gridCenterY - 6, true);

    }

    public void addPentaDecathlon() {
        grid.setCell(gridCenterX, gridCenterY + 4, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 4, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 4, true);
        grid.setCell(gridCenterX - 1, gridCenterY + 4, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 4, true);
        grid.setCell(gridCenterX, gridCenterY + 5, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 5, true);
        grid.setCell(gridCenterX - 1, gridCenterY + 5, true);
        grid.setCell(gridCenterX, gridCenterY + 6, true);

        grid.setCell(gridCenterX, gridCenterY - 3, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 3, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 3, true);
        grid.setCell(gridCenterX - 1, gridCenterY - 3, true);
        grid.setCell(gridCenterX - 2, gridCenterY - 3, true);
        grid.setCell(gridCenterX, gridCenterY - 4, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 4, true);
        grid.setCell(gridCenterX - 1, gridCenterY - 4, true);
        grid.setCell(gridCenterX, gridCenterY - 5, true);

    }

    public void addGlider(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY - 1, true);
        grid.setCell(gridCenterX + 1, gridCenterY - 2, true);
    }

    public void addLightWeightSpaceship(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX - 1, gridCenterY, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 1, true);
        grid.setCell(gridCenterX - 2, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 1, gridCenterY + 3, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 1, true);
        grid.setCell(gridCenterX + 2, gridCenterY + 2, true);

    }

    public void addMiddleWeightSpaceship(){
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX + 3, gridCenterY, true);
        grid.setCell(gridCenterX + 4, gridCenterY, true);
        grid.setCell(gridCenterX + 4, gridCenterY -1, true);
        grid.setCell(gridCenterX + 4, gridCenterY -2, true);
        grid.setCell(gridCenterX + 3, gridCenterY -3, true);
        grid.setCell(gridCenterX + 1, gridCenterY -4, true);
        grid.setCell(gridCenterX-1, gridCenterY -1, true);
        grid.setCell(gridCenterX-1, gridCenterY -3, true);


    }

    public void addHeavyWeightSpaceship() {
        grid.setCell(gridCenterX, gridCenterY, true);
        grid.setCell(gridCenterX + 1, gridCenterY, true);
        grid.setCell(gridCenterX + 2, gridCenterY, true);
        grid.setCell(gridCenterX + 3, gridCenterY, true);
        grid.setCell(gridCenterX + 4, gridCenterY, true);
        grid.setCell(gridCenterX + 5, gridCenterY, true);
        grid.setCell(gridCenterX + 5, gridCenterY -1, true);
        grid.setCell(gridCenterX + 5, gridCenterY -2, true);
        grid.setCell(gridCenterX + 4, gridCenterY -3, true);
        grid.setCell(gridCenterX + 2, gridCenterY -4, true);
        grid.setCell(gridCenterX+1, gridCenterY -4, true);
        grid.setCell(gridCenterX-1, gridCenterY -1, true);
        grid.setCell(gridCenterX-1, gridCenterY -3, true);

    }
}
