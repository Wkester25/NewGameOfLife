import java.awt.*;

public class Cell {
    private int age = 0;
    public boolean alive = false;
    private boolean hasBeenAlive = false;
    private Color cellColor = Color.GREEN;

    public void kill(){
        alive = false;
    }

    public void revive(){
        alive = true;
    }

    public boolean getState(){
        return alive;
    }

    public void ageUp(){
        age++;
    }

    public void hasBeenAlive(){
        hasBeenAlive = true;
    }

    public boolean getHasBeenAlive(){
        return hasBeenAlive;
    }

    public Color getCellColor(){
        Color temp = cellColor;
        updateColor();
        return temp;
    }

    public void updateColor(){
        Color CLOSE = Color.GREEN;
        Color FAR = Color.RED;
        int ratio = age / 5;
        int red = (int)Math.abs((ratio * FAR.getRed()) + ((1 - ratio) * CLOSE.getRed()));
        int green = (int)Math.abs((ratio * FAR.getGreen()) + ((1 - ratio) * CLOSE.getGreen()));
        int blue = (int)Math.abs((ratio * FAR.getBlue()) + ((1 - ratio) * CLOSE.getBlue()));
        System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);
        cellColor = new Color(red, green, blue);
    }


    public void resetAge() {
        age = 0;
    }
    public void resetHasBeenAlive(){
        hasBeenAlive = false;
    }

    public void setCellColor(Color c){
        cellColor = c;
    }
}
