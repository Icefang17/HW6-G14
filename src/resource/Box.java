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
        Domain: {1,3,5,6,7}     contains values between 1 and 9 and will be single
                                valued upon set (i.e; fill 1 -> {1}) the domain will
                                be EMPTY if a constraint is violated
     */

    private Box(int rowIndex, int colIndex, int blkIndex, ArrayList<Group> parents){
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

    public static Box CreateBox(int rowIndex, int colIndex, int blkIndex, Group row, Group column, Group block){
        if(rowIndex < 0 || rowIndex >= 9 || colIndex < 0 || colIndex >= 9 || blkIndex < 0 || blkIndex >= 9){
            System.out.printf("ERROR: Invalid indexing during box creation.");
            return null;
        }
        if(row != null && column != null && block != null){
            ArrayList<Group> list = new ArrayList<>();
            list.add(row);
            list.add(column);
            list.add(block);
            return new Box(rowIndex, colIndex, blkIndex, list);
        }
        System.out.println("ERROR: Null group during box creation.");
        return null;
    }

    public void initializeDomain(){
        for(int i = 1; i < 10; i++){
            domain.add(i);
        }
    }

    public void domainInference(Integer value){
        domain.clear();
        domain.add(value);
    }

    public void restrictNeighboringDomains(Integer value){
        parentRow.restrictDomains(value);
        parentColumn.restrictDomains(value);
        parentBlock.restrictDomains(value);
    }

    public boolean checkValidity(){
        if(parentRow.isComplete() == (-2) || parentColumn.isComplete() == (-2) || parentBlock.isComplete() == (-2))
            return false;
        return true;
    }

    public boolean isSet(){
        if(number > 0 || isSet)
            return true;
        return false;
    }

    public void setNumber(int number){this.number = number;}

    public int getNumber(){return number;}

    public int getRowIndex(){return rowIndex;}

    public int getColIndex(){return colIndex;}

    public int getBlkIndex(){return blkIndex;}

    public boolean getIsSet(){return isSet;}

    public void set(boolean set){this.isSet = set;}

    public Row getParentRow(){return parentRow;}

    public Column getParentColumn(){return parentColumn;}

    public Block getParentBlock(){return parentBlock;}

    public ArrayList<Integer> getDomain(){return domain;}

}
