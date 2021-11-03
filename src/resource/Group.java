package resource;

import java.util.ArrayList;

public class Group {

    private ArrayList<Box> children;
    private Constraint type;

    protected Group(int index, Constraint type){
        this.type = type;
        this.children = new ArrayList<>();
    }

    public ArrayList<Box> getChildren(){return children;}

    public Constraint getType(){return type;}
}
