package resource;

import java.awt.Point;

public class Pair {
    private Point var;
    private Integer value;

    public Pair(Point location, Integer value){
        this.value = value;
        this.var = location;
    }
    public Pair(int x, int y, Integer value){
        this.value = value;
        this.var.setLocation(x, y);
    }

    public Point getLocation(){return var;}

    public int getValue(){return value;}
}
