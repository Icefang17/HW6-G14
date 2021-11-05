package resource;

import java.util.ArrayList;

public class Box {
    private int number;
    private int rowIndex;
    private int colIndex;
    private int blkIndex;
    private Row parentRow;
    private Column parentColumn;
    private Block parentBlock;
    private ArrayList<Integer> domain;

    private Box(int rowIndex, int colIndex, int blkIndex, ArrayList<Group> parents){
        this.number = 0;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.blkIndex = blkIndex;
        this.parentRow = (Row)parents.get(0);
        this.parentColumn = (Column)parents.get(1);
        this.parentBlock = (Block)parents.get(2);
        this.domain = new ArrayList<>();
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

    public void setNumber(int number){this.number = number;}

    public int getNumber(){return number;}

    public int getRowIndex(){return rowIndex;}

    public int getColIndex(){return colIndex;}

    public int getBlkIndex(){return blkIndex;}

    public Row getParentRow(){return parentRow;}

    public Column getParentColumn(){return parentColumn;}

    public Block getParentBlock(){return parentBlock;}

    public ArrayList<Integer> getDomain(){return domain;}

}
