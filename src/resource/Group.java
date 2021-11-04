package resource;

import java.util.ArrayList;

public class Group {

    private ArrayList<Box> children;
    private Constraint type;

    protected Group(int index, Constraint type){
        this.type = type;
        this.children = new ArrayList<>();
    }

    public boolean isComplete(){
        if(children.size() != 9){
            System.out.println("ERROR: Group does not have 9 children.");
            return false;
        }
        for(int i = 0; i < children.size(); i++){
            if(children.get(i).getNumber() == 0)
                return false;
        }
        return true;
    }

    public ArrayList<Box> getChildren(){return children;}

    public Constraint getType(){return type;}
}
