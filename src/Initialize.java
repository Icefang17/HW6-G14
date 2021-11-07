import java.util.ArrayList;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import map.State;
import resource.Pair;
import search.BTS;

public class Initialize {
    // Problem 1.
    public static final int set1[][] = {
        {2, 0, 1}, {5, 0, 2},
        {2, 1, 5}, {5, 1, 6}, {7, 1, 3},
        {0, 2, 4}, {1, 2, 6}, {5, 2, 5},
        {3, 3, 1}, {5, 3, 4},
        {0, 4, 6}, {3, 4, 8}, {6, 4, 1}, {7, 4, 4}, {8, 4, 3},
        {4, 5, 9}, {6, 5, 5}, {8, 5, 8},
        {0, 6, 8}, {4, 6, 4}, {5, 6, 9}, {7, 6, 5},
        {0, 7, 1}, {3, 7, 3}, {4, 7, 2},
        {2, 8, 9}, {6, 8, 3}
    };

    // Problem 2.
    public static final int set2[][] = {
        {2, 0, 5}, {4, 0, 1},
        {2, 1, 2}, {5, 1, 4}, {7, 1, 3},
        {0, 2, 1}, {2, 2, 9}, {6, 2, 2}, {8, 2, 6},
        {0, 3, 2}, {4, 3, 3},
        {1, 4, 4}, {6, 4, 7},
        {0, 5, 5}, {5, 5, 7}, {8, 5, 1},
        {3, 6, 6}, {5, 6, 3},
        {1, 7, 6}, {3, 7, 1},
        {4, 8, 7}, {7, 8, 5}
    };

    // Problem 3.
    public static final int set3[][] = {
        {0, 0, 6}, {1, 0, 7},
        {1, 1, 2}, {2, 1, 5},
        {1, 2, 9}, {3, 2, 5}, {4, 2, 6}, {6, 2, 2},
        {0, 3, 3}, {4, 3, 8}, {6, 3, 9},
        {6, 4, 8}, {8, 4, 1},
        {3, 5, 4}, {4, 5, 7},
        {2, 6, 8}, {3, 6, 6}, {7, 6, 9},
        {7, 7, 1},
        {0, 8, 1}, {2, 8, 6}, {4, 8, 5}, {7, 8, 7}
    };

    public static void main(String[] args) {
        NumberFormat format = new DecimalFormat("#0.00");
        ArrayList<Pair> assignment = new ArrayList<>();
        State csp1 = new State();
        State csp2 = new State();
        State csp3 = new State();

        // Problem 1.
        long startTime = System.currentTimeMillis();
        System.out.println("\n\tProblem 1");
        System.out.println("-------------------------");

        // Fill initial state 1.
        fillBoard(set1, csp1);
        csp1.calculateDomains();
        System.out.println("\tInitial State");
        csp1.printBoard();

        // Solve problem 1.
        assignment = BTS.backtrackingSearch(csp1);
        printSteps(assignment);

        // Update board.
        fillBoard(assignment, csp1);
        System.out.println("\tSolved State");
        csp1.printBoard();
        assignment.clear();

        // End Problem 1.
        System.out.println("-------------------------");
        long endTime = System.currentTimeMillis();
        System.out.println("Problem 1 took " + format.format((endTime - startTime) / 1000d) + " seconds.");

        // Problem 2.
        startTime = System.currentTimeMillis();
        System.out.println("\n\tProblem 2");
        System.out.println("-------------------------");

        // Fill initial state 2.
        fillBoard(set2, csp2);
        csp2.calculateDomains();
        System.out.println("\tInitial State");
        csp2.printBoard();

        // Solve problem 2.
        assignment = BTS.backtrackingSearch(csp2);
        printSteps(assignment);

        // Update board.
        fillBoard(assignment, csp2);
        System.out.println("\tSolved State");
        csp2.printBoard();
        assignment.clear();

        // End Problem 2.
        System.out.println("-------------------------");
        endTime = System.currentTimeMillis();
        System.out.println("Problem 2 took " + format.format((endTime - startTime) / 1000d) + " seconds.");

        // Problem 3.
        startTime = System.currentTimeMillis();
        System.out.println("\n\tProblem 3");
        System.out.println("-------------------------");

        // Fill initial state 3.
        fillBoard(set3, csp3);
        csp3.calculateDomains();
        System.out.println("\tInitial State");
        csp3.printBoard();

        // Solve problem 3.
        assignment = BTS.backtrackingSearch(csp3);
        printSteps(assignment);

        // Update board.
        fillBoard(assignment, csp3);
        System.out.println("\tSolved State");
        csp3.printBoard();
        assignment.clear();

        // End Problem 3.
        System.out.println("-------------------------");
        endTime = System.currentTimeMillis();
        System.out.println("Problem 3 took " + format.format((endTime - startTime) / 1000d) + " seconds.");
    }

    /**
     * Fill the board from an initial set of values.
     * @param set an array of integer arrays, each with an x and y index, and value
     * @param state the csp to fill.
     */
    public static void fillBoard(int set[][], State state){
        for(int i = 0; i < set.length; i++){
            state.getBox(set[i][0], set[i][1]).setNumber(set[i][2]);
        }
    }

    /**
     * Fill the board from a list of assignments.
     * @param set an array of pairs with an x and y index, and value
     * @param state the csp to fill.
     */
    public static void fillBoard(ArrayList<Pair> set, State state){
        for(int i = 0; i < set.size(); i++){
            state.getBox(set.get(i).getLocation()).setNumber(set.get(i).getValue());
        }
    }

    private static void printSteps(ArrayList<Pair> set) {
        for(int i = 0; i < 5; i++) {
            Pair curAssignment = set.get(i);
            Point var = curAssignment.getLocation();
            Integer value = curAssignment.getValue();
            System.out.println("Step: [" + (i + 1) + "]");
            System.out.println("Location: [" + var.x + ", " + var.y + "]");
            System.out.println("Value: [" + value.intValue() + "]\n");
        }
    }
}
