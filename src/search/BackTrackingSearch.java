/*
package search;

import java.awt.Point;
import java.util.ArrayList;

import resource.*;
import map.*;

public class BackTrackingSearch {
    public State backtrackingSearch(State csp) { // returns a solution, or failure
        State assignment = new State(csp);
        return backtrack(assignment, csp);
    }

    public State backtrack(State assignment, State csp) { // returns a solution, or failure
        if(assignment.isComplete())
            return assignment;

        // Select the box with the fewest possible values, or with the most available group spaces on tie break.
        int var = selectUnassignedVariable(csp); // MRV w/ Degree Heuristic function 

        for(int i = 1; i <= 9; i++) {
            if(assignment.isConsistent(i)) { // 
                // add {var = value} to assignment;
                assignment.setValue(var, i);
                var.setNumber(i);

                // Forward Check - infer new domain reductions on the neighboring variables
                ArrayList<ArrayList<Integer>> inferences = new ArrayList<>();
                inferences = inference(csp, var, i);

                if(!inferences.isFailure()) {
                    assignment.inferences.add(inferences);
                    State result = new State(backtrack(assignment, csp)); // Recursive call

                    if(!result.isFailure())
                        return result;
                }
            }

            assignment.resetBox(var);
            // assignment.remove({var = value});
            // assignment.inferences.remove(inferences);
        }

        return null;
    }

    // Will select the variable with the smallest domain.
    // If there is a tie, will choose the variable with the most empty boxes in its parent sets.
    private Box selectUnassigedVariable(State csp) {
        // Select the variable from the problem with the fewest possible values ie smallest domain
        // Breaks ties by favoring the variable with the most cumulative open spaces in its row, col, and block.
        ArrayList<Integer> domain = new ArrayList<>();

        foreach(Box var : csp.variables) {
            ArrayList<Integer> prevDomain = new ArrayList<>(domain);

            ArrayList<Box> row = Box.getParentRow().getChildren();
            for(int i = 0; i < row.size()) {
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

            if(prevDomain.size() < domain.size())
                domain = prevDomain;

            
        }
    }

    private ArrayList<ArrayList<Integer>> inference(State csp, Box var, int value) {
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
}*/
