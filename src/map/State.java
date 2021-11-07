package map;

import resource.*;

import java.awt.Point;
import java.util.ArrayList;

public class State {
    public ArrayList<Group> rows;
    public ArrayList<Group> cols;
    public ArrayList<Group> blks;

    /**
     * State constructor.
     */
    public State() {
        this.rows = new ArrayList<>();
        this.cols = new ArrayList<>();
        this.blks = new ArrayList<>();
        instantiateBoard();
    }

    /**
     * Initializes all groups.
     * Initializes all board values to 0.
     */
    private void instantiateBoard() {
        // Create 9 rows, columns, and blocks.
        for(int i = 0; i < 9; i++) {
            rows.add(new Row());
            cols.add(new Column());
            blks.add(new Block());
        }

        // Iterate over the rows.
        for(int y = 0; y < 9; y++) {
            // Align blocks.
            int startBlock = 0;
            if(y >= 3)
                startBlock = 3;
            if(y >= 6)
                startBlock = 6;

            // Iterate over the columns.
            for(int x = 0; x < 9; x++) {
                // Increment every 3 groups.
                if (x % 3 == 0 && x > 0)
                    startBlock++;

                // Create a new box at the {row, column}.
                Box box = Box.CreateBox(y, x, startBlock, rows.get(y), cols.get(x), blks.get(startBlock));
                box.getParentRow().addChild(box);
                box.getParentColumn().addChild(box);
                box.getParentBlock().addChild(box);
            }
        }
    }

    /**
     * Sets the value of the box.
     * @param location the coordinates of the box as a point.
     * @param number the value to set the box to.
     * @return the number on success, 0 on failure.
     */
    public int setBoxNumber(Point location, int number) {
        // Check that the location is within the bounds.
        if(location.x >= 0 && location.x < 9 && location.y >= 0 && location.y < 9){
            // Check that the value is within the bounds.
            if(number > 0 && number <= 9){
                // Set the value.
                Box box = rows.get(location.y).getChildren().get(location.x);
                box.setNumber(number);
                
                return number;
            }
        }
        
        return 0;
    }

    /**
     * Gets the Box object, given its coordinates as a pair of integers.
     * @param x the x index of the box
     * @param y the y index of the box
     * @return the box
     */
    public Box getBox(int x, int y){return rows.get(y).getChildren().get(x);}

    /**
     * Gets the Box object, given its coordinates as a point.
     * @param location the location of the box
     * @return the box
     */
    public Box getBox(Point location){return rows.get(location.y).getChildren().get(location.x);}

    /**
     * Checks if all groups are complete.
     * @return true if all groups are complete, else false
     */
    public boolean isComplete() {
        // Iterate over all sets of groups.
        for(int i = 0; i < 9 ; i++) {
            // Check that each group has variables to fill.
            if(rows.get(i).isComplete() > 0 || cols.get(i).isComplete() > 0 || blks.get(i).isComplete() > 0)
                return false;
        }

        return true;
    }

    /**
     * Calculate the domains of the csp.
     */
    public void calculateDomains() {
        // Iterate over rows.
        for(int y = 0; y < 9; y++) {
            // Iterate over columns.
            for(int x = 0; x < 9; x++){
                Box curBox = rows.get(y).getChild(x);

                // Check that the box has not been set.
                if(curBox.isSet()) {
                    // Clear the boxes domain and set it to its current value.
                    curBox.getDomain().clear();
                    curBox.getDomain().add(curBox.getNumber());
                    curBox.set(true);
                }

                // Update the boxes new domain.
                curBox.updateDomain();
            }
        }
    }

    /**
     * Prints the state.
     */
    public void printBoard() {
        // Iterate over rows.
        for(int i = 0; i < 9; i++){
            String row = new String();

            // Iterate over columns.
            for(int j = 0; j < 9; j++){
                // Select every 3rd column.
                if(j % 3 == 0 && j > 0)
                    row = row.concat("|  ");

                row = row.concat("|" + getBox(j, i).getNumber());
            }

            row = row.concat("|");

            // Select every 3rd row.
            if(i % 3 == 0 && i > 0)
                System.out.println("");

            System.out.println(row);
        }

        System.out.println("");
    }
}
