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
    public static final int set3[][] = {{0,0,6},{1,0,7},{1,1,2},{2,1,5},{1,2,9},{3,2,5},{4,2,6},
                                        {6,2,2},{0,3,3},{4,3,8},{6,3,9},{6,4,8},{8,4,1},{3,5,4},
                                        {4,5,7},{2,6,8},{3,6,6},{7,6,9},{7,7,1},{0,8,1},{2,8,6},
                                        {4,8,5},{7,8,7}};

    public static void main(String[] args){
        ArrayList<Pair> assignment = new ArrayList<>();
        State csp1 = new State();
        State csp2 = new State();
        State csp3 = new State();

        fillBoard(set1, csp1);
        csp1.calculateDomains();
        System.out.println("Initial State");
        csp1.printBoard();
        assignment = BTS.backtrackingSearch(csp1);
        fillBoard(assignment, csp1);
        csp1.printBoard();
        assignment.clear();

        fillBoard(set2, csp2);
        csp2.calculateDomains();
        System.out.println("Initial State");
        csp2.printBoard();
        assignment = BTS.backtrackingSearch(csp2);
        fillBoard(assignment, csp2);
        csp2.printBoard();
        assignment.clear();

        fillBoard(set3, csp3);
        csp3.calculateDomains();
        System.out.println("Initial State");
        csp3.printBoard();
        assignment = BTS.backtrackingSearch(csp3);
        fillBoard(assignment, csp3);
        csp3.printBoard();
        assignment.clear();
    }

    public static void fillBoard(int set[][], State state){
        for(int i = 0; i < set.length; i++){
            state.getBox(set[i][0], set[i][1]).setNumber(set[i][2]);
        }
    }

    public static void fillBoard(ArrayList<Pair> set, State state){
        for(int i = 0; i < set.size(); i++){
            state.getBox(set.get(i).getLocation()).setNumber(set.get(i).getValue());
        }
    }
}
