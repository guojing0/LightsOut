package a10;

import javax.swing.*;
import java.awt.*;

/**
 * A helper class for LightsOut class
 *
 * @author Jing Guo
 */
public class LightsOutButton extends JButton {

    private boolean status; // true is on, false off
    private int row, col;

    /**
     * Constructor method for this class
     * <p>
     * If the light is on, its status is true, otherwise false
     *
     * @param row row of the button
     * @param col column of the button
     */
    public LightsOutButton(int row, int col) {
        this.status = false;
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the current status of the light
     *
     * @return true if the light is on, otherwise false
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Switch the status of the light to its opposite
     * <p>
     * If the light is on, then off, and vice versa
     */
    public void switchStatus() {
        this.status = !this.status;
    }

    /**
     * Returns the row of the button
     *
     * @return row of the button
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the button
     *
     * @return column of the button
     */
    public int getCol() {
        return col;
    }

    /**
     * If the light is on, its color is white, otherwise black
     */
    public void changeColor() {
        if (status) {
            setBackground(Color.white);
        } else {
            setBackground(Color.black);
        }
    }
}
