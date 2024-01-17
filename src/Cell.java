import java.awt.*;
/**
 * HONOR PLEDGE: All work here is honestly obtained and is my own.  Signed:  James Kester
 * @author kesterJ
 * Date of Completion: 1/12/2024
 * Assignment:     Game of Life
 *
 * Attribution:  Original Game of Life GUI
 *
 * General Description:   this is the custom cell class that holds the cell's states and color
 *
 * Advanced:  	Class design
 *
 * Errata:
 *
 */


public class Cell {
    private int age = 0;
    public boolean alive = false;
    public boolean hasBeenAlive = false;
    private float cellHue = .67f;

    /**
     * Kills the cell
     */
    public void kill(){
        alive = false;
    }

    /**
     * Revives the cell
     */
    public void revive(){
        alive = true;
    }

    /**
     *  Returns the state of the cell
     * @return
     */
    public boolean getState(){
        return alive;
    }

    /**
     * Ages the cell by one
     */
    public void ageUp(){
        age++;
    }

    /**
     * Returns the age of the cell
     * @return
     */
    public int getAge(){
        return age;
    }

    /**
     * Sets the age of the cell
     * @param age
     */
    public void setAge(int age){
        this.age = age;
    }

    /**
     *  Returns the color of the cell
     */
    public void hasBeenAlive(){
        hasBeenAlive = true;
    }

    /**
     * Returns whether or not the cell has been alive
     * @return
     */
    public boolean getHasBeenAlive(){
        return hasBeenAlive;
    }

    /**
     *  Returns the color of the cell
     * @return
     */
    public Color getCellColor(){
        float hue = cellHue-(age*.01f);
        if (hue < 0) hue = 0;
        return Color.getHSBColor(hue, 1f, 1f);
    }


    /**
     * Resets the age of the cell
     */
    public void resetAge() {
        age = 0;
    }

    /**
     * Resets the hasBeenAlive of the cell
     */
    public void resetHasBeenAlive(){
        hasBeenAlive = false;
    }

}
