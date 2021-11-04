import resource.*;

import java.util.ArrayList;

public class Initialize {

    public static void main(String[] args){
        ArrayList<Group> rows = new ArrayList<>();
        ArrayList<Group> cols = new ArrayList<>();
        ArrayList<Group> blks = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            rows.add(new Row());
            cols.add(new Column());
            blks.add(new Block());
        }

        for(int y = 0; y < 9; y++){
            int startBlock = 0;
            if(y >= 3)
                startBlock = 3;
            if(y >= 6)
                startBlock = 6;
            for(int x = 0; x < 9; x++){
                if(x % 3 == 0 && x > 0)
                    startBlock++;
                Box box = Box.CreateBox(y, x, startBlock, rows.get(y), cols.get(x), blks.get(startBlock));
                box.getParentRow().addChild(box);
                box.getParentColumn().addChild(box);
                box.getParentBlock().addChild(box);
            }
        }
    }

}
