package search;

import java.awt.Point;
import java.util.ArrayList;

import resource.*;

// What is assignment? inference?
// Should the map be a 2d array? Groups of constraints?
// Is the CSP just the start state here?
// Order Domain values doesn't actually matter here does it?

public class BackTrackingSearch {
    public ArrayList<Pair> backtrackingSearch(Problem csp) { // returns a solution, or failure
        return backtrack({}, csp); // Will return an array of assignments?
    }

    public ArrayList<Pair> backtrack(ArrayList<Pair> assignment, Problem csp) { // returns a solution, or failure
        if(assignment.isComplete())
            return assignment;

        // Select the box with the fewest possible values, or with the most available group spaces on tie break.
        int var = selectUnassignedVariable(csp); // MRV w/ Degree Heuristic function 

        for(int value : orderDomainValues(var, assignment, csp)) { // Least Constraining Value function
            if(assignment.isConsistent(value)) {
                // add {var = value} to assignment;
                assignment.add({var = value});

                // Forward Check - infer new domain reductions on the neighboring variables
                inferences = inference(csp, var, value);

                if(!inferences.isFailure()) {
                    assignment.inferences.add(inferences);
                    Assignment result = backtrack(assignment, csp); // Recursive call

                    if(!result.isFailure())
                        return result;
                }
            }

            assignment.remove({var = value});
            assignment.inferences.remove(inferences);
        }

        return null;
    }

    // Will select the variable with the smallest domain.
    // If there is a tie, will choose the variable with the most empty boxes in its parent sets.
    private Box selectUnassigedVariable(Problem csp) {
        // Select the variable from the problem with the fewest possible values ie smallest domain
        // Breaks ties by favoring the variable with the most cumulative open spaces in its row, col, and block.
        ArrayList<Integer> domain = new ArrayList<>();

        foreach(Box var : csp.variables) {
            ArrayList<Box> row = Box.getParentRow().getChildren();
            for(int i = 0; i < row.length) {
                if(!domain.contains(row.get(i).getNumber()))
                    domain.add(row.get(i).getNumber());
            }

            ArrayList<Box> column = Box.getParentColumn().getChildren();
            for(int i = 0; i < row.length) {
                if(!domain.contains(row.get(i).getNumber()))
                    domain.add(row.get(i).getNumber());
            }

            ArrayList<Box> row = Box.getParentBlock().getChildren();
            for(int i = 0; i < row.length) {
                if(!domain.contains(row.get(i).getNumber()))
                    domain.add(row.get(i).getNumber());
            }
        }
    }

    // Order the domain by least constrained value.
    // This is arbitrary, domain values all share the same constraints.
    private int orderDomainValues(Box var, ArrayList<Pair> assignment, Problem csp) {
        return csp.domain;
        // Orders the domain values for the current variable by least constrained value.
        // Basically computes the domain from all 3 directions and orders it by ???.
    }

    private domain? inference(Problem csp, Box var, int value) {
        // Establishes arc consistency for the current variable.
        
        // Remove value from surrounding domains. If any domains are empty, return null
        // get boxes from csp at [var], remove value from row, col, block domain.
    }
}

for(int i = 0; i < 9; i++) {
    if !rows.get(i).isComplete
        return false;
    if !cols.get(i).isComplete  
        return false;
}