package map;

import resource.*;

import java.awt.*;
import java.util.ArrayList;

public class State {
    private static final int POSSIBLE_VALUES[] = {1,2,3,4,5,6,7,8,9};
    public ArrayList<Group> rows;
    public ArrayList<Group> cols;
    public ArrayList<Group> blks;

    public State(){
        rows = new ArrayList<>();
        cols = new ArrayList<>();
        blks = new ArrayList<>();
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
}
