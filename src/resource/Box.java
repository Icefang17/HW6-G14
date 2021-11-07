package resource;

import java.util.ArrayList;

public class Box {
    private int number;
    private int rowIndex;
    private int colIndex;
    private int blkIndex;
    private boolean isSet;
    private Row parentRow;
    private Column parentColumn;
    private Block parentBlock;
    private ArrayList<Integer> domain;

    /*
        Domain -> {1, 2, 3, 4, 5, 6, 7, 8, 9}     
        Has single value upon being set (i.e., Domain -> {1})
        Has no value if a constraint is violated (i.e., Domain -> {})
     */

    /**
     * Box constructor.
     * @param rowIndex the index of the row the box is on
     * @param colIndex the index of the column the box is on
     * @param blkIndex the index of the block the box is on
     * @param parents the parent groups of the box.
     */
    private Box(int rowIndex, int colIndex, int blkIndex, ArrayList<Group> parents) {
        this.number = 0;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.blkIndex = blkIndex;
        this.isSet = false;
        this.parentRow = (Row)parents.get(0);
        this.parentColumn = (Column)parents.get(1);
        this.parentBlock = (Block)parents.get(2);
        this.domain = new ArrayList<>();
        initializeDomain();
    }

    /**
     * Box factory.
     * @param rowIndex the index of the row the box is on
     * @param colIndex the index of the column the box is on
     * @param blkIndex the index of the block the box is on
     * @param row the parent row of the box
     * @param column the parent column of the box
     * @param block the parent block of the box
     * @return a new box
     */
    public static Box CreateBox(int rowIndex, int colIndex, int blkIndex, Group row, Group column, Group block) {
        // Check input is between 0-9.
        if(rowIndex < 0 || rowIndex >= 9 || colIndex < 0 || colIndex >= 9 || blkIndex < 0 || blkIndex >= 9)
            return null;

        // Check groups have been initialized.
        if(row != null && column != null && block != null) {
            ArrayList<Group> list = new ArrayList<>();
            
            // Add groups to parent list.
            list.add(row);
            list.add(column);
            list.add(block);

            // Create new box.
            return new Box(rowIndex, colIndex, blkIndex, list);
        }

        return null;
    }

    /**
     * Initializes the domain of the box to 1-9.
     */
    public void initializeDomain() {
        for(int i = 1; i < 10; i++){
            domain.add(i);
        }
    }

    /**
     * Replaces a value in the domain of the box.
     * @param value the value to replace
     */
    public void domainInference(Integer value) {
        // Check that the value is not already in the domain.
        if(!domain.contains(value))
            domain.add(value);

        sortDomain();
    }

    /**
     * Sorts the domain in increasing order.
     */
    public void sortDomain() {
        for(int i = 0; i < domain.size(); i++) {
            for(int j = i + 1; j < domain.size(); j++) {
                Integer tmp = 0;

                if(domain.get(i) > domain.get(j)) {
                    tmp = domain.get(i);
                    domain.set(i, domain.get(j));
                    domain.set(j, tmp);
                }
            }
        }
    }

    /**
     * Updates the domain to reflect set boxes.
     */
    public void updateDomain() {
        // Remove the current domain.
        domain.clear();

        // Check that the box is set.
        if(isSet()) {
            // Add the value of the box to its domain.
            domain.add(number);
        } else {
            // Set the domain to 1-9.
            initializeDomain();

            // Remove conflicting domains.
            checkDomain(parentRow);
            checkDomain(parentColumn);
            checkDomain(parentBlock);
        }

        sortDomain();
    }

    /**
     * Removes conflicting domains from a box, given its parent groups.
     * @param parent the boxes parent groups
     */
    private void checkDomain(Group parent) {
        // Iterate through all possible domains.
        for(int i = 0; i < 9; i++) {
            Integer number = parent.getChild(i).getNumber();

            // If the value for the neighbor is set, remove it from the boxes domain.
            if(number > 0)
                domain.remove(number);
        }

        sortDomain();
    }

    /**
     * Restricts all neighboring domains of the box.
     * @param value the value of the box
     */
    public void restrictNeighboringDomains(Integer value) {
        // Restrict all groups.
        parentRow.restrictDomains(value);
        parentColumn.restrictDomains(value);
        parentBlock.restrictDomains(value);
    }

    /**
     * Checks if all neighboring boxes are filled.
     * @return false if all neighboring boxes are filled, else true
     */
    public boolean checkValidity() {
        if(parentRow.isComplete() == (-2) || parentColumn.isComplete() == (-2) || parentBlock.isComplete() == (-2))
            return false;

        return true;
    }

    /**
     * Determines if the box is set.
     * @return true if the box is set, else false
     */
    public boolean isSet() {
        if(number > 0 || isSet)
            return true;

        return false;
    }

    /**
     * Sets the value of the box to a given number.
     * @param number the number to set the box to
     */
    public void setNumber(int number){this.number = number;}

    /**
     * Gets the value of the box.
     * @return the value of the box
     */
    public int getNumber(){return number;}

    /**
     * Gets the row index of the box.
     * @return the row index of the box
     */
    public int getRowIndex(){return rowIndex;}

    /**
     * Gets the column index of the box.
     * @return the column index of the box
     */
    public int getColIndex(){return colIndex;}

    /**
     * Gets the block index of the box.
     * @return the block index of the box
     */
    public int getBlkIndex(){return blkIndex;}

    /**
     * Gets whether the box contains a value.
     * @return true if the box is set, else false.
     */
    public boolean getIsSet(){return isSet;}

    /**
     * Sets whether the box contains a value.
     * @param set true if it contains a value, else false.
     */
    public void set(boolean set){this.isSet = set;}

    /**
     * Gets the parent row of the box.
     * @return the the parent row of the box
     */
    public Row getParentRow(){return parentRow;}

    /**
     * Gets the parent column of the the box.
     * @return the parent column of the box
     */
    public Column getParentColumn(){return parentColumn;}

    /**
     * Gets the parent block of the box.
     * @return the parent block of the box
     */
    public Block getParentBlock(){return parentBlock;}

    /**
     * Gets the domain of the box.
     * @return the domain of the box
     */
    public ArrayList<Integer> getDomain(){return domain;}
}
