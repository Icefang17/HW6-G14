package search;

public class BackTrackingSearch {
    public void BACKTRACKING-SEARCH(CSP csp) { // returns a solution, or failure
        return BACKTRACK({}, csp);
    }

    public void BACKTRACK(Assignment assignment, CSP csp) { // returns a solution, or failure
        if(assignment is complete) {
            return assignment;
        }

        Variable var = SELECT-UNASSIGNED-VARIABLE(csp); // MRV w/ Degree Heuristic function 

        for(Value value : ORDER-DOMAIN-VALUES(var , assignment, csp)) { // Least Constraining Value function
            if(value is consistent with assignment) {
                add {var = value} to assignment;
                inferences = INFERENCE(csp, var , value); // Foward Checking function

                if(inferences != failure) {
                    add inferences to assignment;
                    result = BACKTRACK(assignment, csp); // Recursive call

                    if(result != failure) {
                        return result; // Exit case
                    }
                }
            }

            remove {var = value} and inferences from assignment;
        }

        return failure;
    }
}
