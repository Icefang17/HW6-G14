package resource;

import java.awt.Point;

public class Pair {
    private Point var;
    private Integer value;

    /**
     * Pair constructor for a point and a value.
     * @param location the location of the variable
     * @param value the value of location
     */
    public Pair(Point location, Integer value) {
        this.value = value;
        this.var = location;
    }

    /**
     * Pair constructor for an x and y index, and a value.
     * @param x the x index of the variable (0 indexed)
     * @param y the y index of the variable (0 indexed)
     * @param value the value of location
     */
    public Pair(int x, int y, Integer value) {
        this.value = value;
        this.var.setLocation(x, y);
    }

    /**
     * Gets the location of a pair.
     * @return the location of the pair
     */
    public Point getLocation(){return var;}

    /**
     * Gets the value of a pair.
     * @return the value of the pair
     */
    public int getValue(){return value;}
}
