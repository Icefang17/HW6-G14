package search;

import java.awt.Point;
import java.util.ArrayList;

public class BackTrackingSearch {
    // Assignments have:
    // A variable to assign
    // A value to assign the variable to
    // An array of inferences 

    public State backtrackingSearch(Problem csp) { // returns a solution, or failure
        
        return backtrack({}, csp); // Will return an array of assignments.
    }

    public Assignment backtrack(Assignment assignment, Problem csp) { // returns a solution, or failure
        if(assignment.isComplete())
            return assignment;

        // Select box to try.
        int var = selectUnassignedVariable(csp); // MRV w/ Degree Heuristic function 

        // Select 
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

    private int selectUnassigedVariable(Problem csp) {
        // Select the variable from the problem with the fewest possible values
        // Breaks ties by favoring the variable with the most cumulative open spaces in its row, col, and block.
    }

    private int orderDomainValues(int var, Assignment assignment, Problem csp) {
        // Orders the domain values for the current variable by least constrained value.
        // Basically computes the domain from all 3 directions and orders it by ???.
    }

    private domain? inference(Problem csp, int var, int value) {
        // Establishes arc consistency for the current variable.
        
        // Remove value from surrounding domains. If any domains are empty, return null
        // get boxes from csp at [var], remove value from row, col, block domain.
    }
}
