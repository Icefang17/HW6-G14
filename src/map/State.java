package map;

import resource.*;

import java.awt.*;
import java.util.ArrayList;

public class State {
    public ArrayList<Group> rows;
    public ArrayList<Group> cols;
    public ArrayList<Group> blks;


    public State(){
        this.rows = new ArrayList<>();
        this.cols = new ArrayList<>();
        this.blks = new ArrayList<>();
        instantiateBoard();
    }

    private void instantiateBoard(){
        for(int i = 0; i < 9; i++){
            rows.add(new Row());
            cols.add(new Column());
            blks.add(new Block());
        }

        for(int y = 0; y < 9; y++) {
            int startBlock = 0;
            if (y >= 3)
                startBlock = 3;
            if (y >= 6)
                startBlock = 6;
            for (int x = 0; x < 9; x++) {
                if (x % 3 == 0 && x > 0)
                    startBlock++;
                Box box = Box.CreateBox(y, x, startBlock, rows.get(y), cols.get(x), blks.get(startBlock));
                box.getParentRow().addChild(box);
                box.getParentColumn().addChild(box);
                box.getParentBlock().addChild(box);
            }
        }
    }

    public int setBoxNumber(Point location, int number){
        if(location.x >= 0 && location.x < 9 && location.y >= 0 && location.y < 9){
            if(number > 0 && number <= 9){
                Box box = rows.get(location.y).getChildren().get(location.x);
                box.setNumber(number);
                return number;
            }
        }
        return 0;
    }

    public Box getBox(int x, int y){
        return rows.get(y).getChildren().get(x);
    }
    public Box getBox(Point location){
        return rows.get(location.y).getChildren().get(location.x);
    }

    // Checks all groups for completeness
    // If a group is not complete, return false
    public boolean isComplete(){
        for(int i = 0; i < 9 ; i++){
            if(rows.get(i).isComplete() > 0 || cols.get(i).isComplete() > 0 || blks.get(i).isComplete() > 0)
                return false;
        }
        return true;
    }

    public void calculateDomains(){
        for(int y = 0; y < 9; y++){
            for(int x = 0; x < 9; x++){
                Box curBox = rows.get(y).getChild(x);
                if(curBox.isSet()){
                    curBox.domainInference(curBox.getNumber());
                    curBox.restrictNeighboringDomains(curBox.getNumber());
                }
            }
        }
    }

    public void printBoard(){
        for(int i = 0; i < 9; i++){
            String row = new String();
            for(int j = 0; j < 9; j++){
                if(j % 3 == 0 && j > 0)
                    row = row.concat("|  ");

                row = row.concat("|" + getBox(j, i).getNumber());
            }

            row = row.concat("|");
            if(i % 3 == 0 && i > 0)
                System.out.println("");

            System.out.println(row);
        }

        System.out.println("\n");
    }
}
