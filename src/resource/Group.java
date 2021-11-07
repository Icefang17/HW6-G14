package resource;

import java.util.ArrayList;

public class Group {

    public static final int MAX = 9;
    private ArrayList<Box> children;
    private Constraint type;

    protected Group(Constraint type){
        this.type = type;
        this.children = new ArrayList<>();
    }

    // Adds a child to the children list
    public void addChild(Box child){
        if(child == null){
            System.out.println("ERROR: Null child passed to group");
            return;
        }
        if(children.size() == MAX){
            System.out.println("ERROR: Attempting to add children to a full");
            return;
        }
        children.add(child);
    }

    // Checks how many children are still empty
    // returns number of unset children or -1 on failure or -2 on finding an invalid domain
    public int isComplete(){
        if(children.size() != MAX){
            System.out.println("ERROR: Group does not have 9 children.");
            return (-1);
        }
        int count = 0;
        for(int i = 0; i < MAX; i++){
            if(children.get(i).getNumber() == 0 && !children.get(i).isSet())
                count++;
            if(children.get(i).getDomain().size() == 0 && !children.get(i).isSet())
                return (-2);
        }
        return count;
    }

    public void restrictDomains(Integer number){
        ArrayList<Box> boxes = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            if(!children.get(i).getDomain().isEmpty() && children.get(i).getDomain().contains(number) && children.get(i).getNumber() != number){
                children.get(i).getDomain().remove(number);
            }
        }
    }

    public void unrestrictDomains(Integer number){
        for(int i = 0; i < 9; i++){
            if(!children.get(i).isSet() && !children.get(i).getDomain().contains(number)){
                children.get(i).getDomain().add(number);
                children.get(i).sortDomain();
            }
        }
    }

    public ArrayList<Box> getChildren(){return children;}

    public Box getChild(int index){return children.get(index);}

    public Constraint getType(){return type;}
}
