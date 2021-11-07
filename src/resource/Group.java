package resource;

import java.util.ArrayList;

public class Group {
    public static final int MAX = 9;
    private ArrayList<Box> children;
    private Constraint type;

    /**
     * Group constructor.
     * @param type ROW, COLUMN, or BLOCK.
     */
    protected Group(Constraint type) {
        this.type = type;
        this.children = new ArrayList<>();
    }

    /**
     * Adds a box to the Groups children list.
     * @param child the box to add.
     */
    public void addChild(Box child) {
        // Check that the child exists, and that no more than MAX children exist in the list.
        if(child == null || children.size() == MAX)
            return;

        // Add the child to the list.
        children.add(child);
    }

    /**
     * Determines if the group is filled.
     * @return the number of open boxes in the group.
     */
    public int isComplete() {
        // Check that children list is full.
        if(children.size() != MAX)
            return -1;

        // Iterate over the group.
        int count = 0;
        for(int i = 0; i < MAX; i++) {
            // Count the number of open boxes.
            if(children.get(i).getNumber() == 0 && !children.get(i).isSet())
                count++;

            // Check if the domain is empty.
            if(children.get(i).getDomain().size() == 0 && !children.get(i).isSet())
                return -2;
        }

        return count;
    }

    /**
     * Remove a value from the domains of a group.
     * @param number the value to remove.
     */
    public void restrictDomains(Integer number) {
        // Iterate over the group.
        for(int i = 0; i < 9; i++) {
            // Check that the domain is not empty.
            // Check that the domain contains the value.
            // Check that the value is not already set.
            if(!children.get(i).getDomain().isEmpty() && children.get(i).getDomain().contains(number) && children.get(i).getNumber() != number)
                children.get(i).getDomain().remove(number);
        }
    }

    /**
     * 
     * @param number
     */
    public void unrestrictDomains(Integer number) {
        // Iterate over the group.
        for(int i = 0; i < 9; i++) {
            // Check that the child is not set.
            // Check that the domain does not already contain the number.
            if(!children.get(i).isSet() && !children.get(i).getDomain().contains(number)) {
                children.get(i).getDomain().add(number);
                children.get(i).sortDomain();
            }
        }
    }

    /**
     * Gets the children of the group.
     * @return the children of the group
     */
    public ArrayList<Box> getChildren(){return children;}

    /**
     * Gets the child at a specific index in the group.
     * @param index the index to get a child from.
     * @return the child selected.
     */
    public Box getChild(int index){return children.get(index);}

    /**
     * Gets the type of the group.
     * @return the type of the group.
     */
    public Constraint getType(){return type;}
}
