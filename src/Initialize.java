import java.util.ArrayList;

import map.State;
import resource.Pair;
import search.BTS;

public class Initialize {

    public static final int set1[][] = {{2,0,1},{5,0,2},{2,1,5},{5,1,6},{7,1,3},{0,2,4},{1,2,6},
                                        {5,2,5},{3,3,1},{5,3,4},{0,4,6},{3,4,8},{6,4,1},{7,4,4},
                                        {8,4,3},{4,5,9},{6,5,5},{8,5,8},{0,6,8},{4,6,4},{5,6,9},
                                        {7,6,5},{0,7,1},{3,7,3},{4,7,2},{2,8,9},{6,8,3}};
    public static final int set2[][] = {{2,0,5},{4,0,1},{2,1,2},{5,1,4},{7,1,3},{0,2,1},{2,2,9},
                                        {6,2,2},{8,2,6},{0,3,2},{4,3,3},{1,4,4},{6,4,7},{0,5,5},
                                        {5,5,7},{8,5,1},{3,6,6},{5,6,3},{1,7,6},{3,7,1},{4,8,7},
                                        {7,8,5}};

    public static void main(String[] args){

        State csp = new State();
        fillBoard(set1, csp);
        csp.calculateDomains();
        System.out.println("Set 1 Initial State");
        csp.printBoard();

        ArrayList<Pair> assignment = new ArrayList<>(BTS.backtrackingSearch(csp));

        csp.printBoard();
    }

    public static void fillBoard(int set[][], State state){
        for(int i = 0; i < set.length; i++){
            state.getBox(set[i][0], set[i][1]).setNumber(set[i][2]);
        }
    }
}
