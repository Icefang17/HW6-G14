package search;

import map.State;
import resource.*;

import java.awt.Point;
import java.util.ArrayList;

public class BTS {
    /**
     * Takes a csp state and solves it by selecting unassigned variables by minimum-remaining-values in the variables domain,
     * and attempting to set it to different values, forward checking neighboring values based on their restricted domains.
     * @param csp the csp to solve
     * @return an array of assignments to solve the csp.
     */
    public static ArrayList<Pair> backtrackingSearch(State csp) {
        return backtrack(new ArrayList<>(), csp);
    }

    /**
     * Recursive function to implement backtracking search.
     * @param assignment an array of assignments to add to
     * @param csp the csp to solve
     * @return the modified array of assignments
     */
    private static ArrayList<Pair> backtrack(ArrayList<Pair> assignment, State csp) {
        // Check that the csp is complete. (Exit case)
        if(csp.isComplete())
            return assignment;

        // Select a variable from the csp by MRV and degree heuristic.
        Point var = selectUnassignedVariable(csp, assignment);
        ArrayList<Integer> domain = csp.getBox(var).getDomain();

        // Iterate over the selected variables domain.
        for(int i = 0; i < domain.size(); i++) {
            ArrayList<Pair> inferences = new ArrayList<>();

            // Check that the value is consistent with the assignment.
            if(csp.getBox(var).checkValidity()) {
                ArrayList<Pair> steps = new ArrayList<>();
                
                // Forward check neighboring variables.
                inferences = inference(csp, var, domain.get(i), steps);

                // Check that forward checking occured.
                if(!inferences.isEmpty()) {
                    // Add the extra inferences to the assignment list.
                    assignment.addAll(inferences);

                    // Attempt a new assignment given the current assignment.
                    ArrayList<Pair> result = backtrack(assignment, csp);
                    
                    // Check that a variable could be selected.
                    if(result != null)
                        return result;
                }
            }

            // Iterate through the inferences.
            for(int j = 0; j < inferences.size(); j++) {
                // Undo inferences.
                csp.getBox(inferences.get(j).getLocation()).set(false);
                csp.getBox(inferences.get(j).getLocation()).setNumber(0);
            }

            // Remove inferences.
            assignment.removeAll(inferences);

            // Recalculate domains.
            csp.calculateDomains();
        }

        return null;
    }

    /**
     * Select an unassigned variable by minimum-remaining-values in the variables domain.
     * Ties are broken by choosing the variable with the most open spaces in its neighbor list.
     * @param state the csp to search
     * @param assignment the assignments already added
     * @return the point in the csp with lowest MRV or highest degree heuristic.
     */
    private static Point selectUnassignedVariable(State state, ArrayList<Pair> assignment) {
        ArrayList<Point> selections = new ArrayList<>();
        Point location = new Point();
        int minDomainSize = 100;

        // Iterate over the rows.
        for(int i = 0; i < 9; i++) {
            // Iterate over the columns.
            for(int j = 0; j < 9; j++) {
                int domainSize = state.getBox(j, i).getDomain().size();
                location.setLocation(j, i);

                // Check that the domain is <= the size of the previous domain.
                // Check that the box is not set.
                // Check that the location does not exist within assignment.
                if(domainSize <= minDomainSize && domainSize > 0 && !state.getBox(j, i).isSet() && !parseAssignment(assignment, location)){
                    // Check that the domain is < the size of the previous domain.
                    if(domainSize < minDomainSize){
                        // Clear the selections and set the new minimum domain.
                        selections.clear();
                        minDomainSize = domainSize;
                    }

                    // Add the selection.
                    selections.add(new Point(j, i));
                }
            }
        }

        // Return a single selection.
        if(selections.size() == 1)
            return selections.get(0);

        // Return the selection with the highest degree heuristic.
        return tieBreaker(selections, state);
    }

    /**
     * Checks if a point exists within an arraylist of pairs.
     * @param assignment the arraylist to search
     * @param location the location to search for
     * @return true if the location is in the assignment, else false.
     */
    private static boolean parseAssignment(ArrayList<Pair> assignments, Point location) {
        // Iterate over all assignments.
        for(Pair assignment : assignments) {
            // Check that the assignment location equals the given location.
            if(assignment.getLocation().equals(location))
                return true;
        }

        return false;
    }

    /**
     * Breaks ties between selections of equal domain sizes by selecting the variable with the highest degree heuristic.
     * Degree Heuristics are determined based on the number of open spaces in the selections parent groups.
     * @param selections an arraylist of possible points to select
     * @param state the csp
     * @return the selected point
     */
    private static Point tieBreaker(ArrayList<Point> selections, State state) {
        Point choice = new Point();
        Box box;
        int maxOpenBoxes = 0;

        // Iterate over the selections.
        for(Point point : selections) {
            int openBoxes = 0;
            box = state.getBox(point);
            
            // Sum the number of open boxes in each selections parent groups.
            openBoxes += box.getParentRow().isComplete() + box.getParentColumn().isComplete() + box.getParentBlock().isComplete();

            // Check that the number of open boxes is greater than the previous selection.
            if(openBoxes > maxOpenBoxes) {
                // Set the new maximum.
                maxOpenBoxes = openBoxes;
                choice.setLocation(point);
            }
        }

        return choice;
    }

    /**
     * Forward check future assignments by propagating constraints recursively.
     * @param csp the csp to reference
     * @param var the variable to check from
     * @param value the value of the variable
     * @param steps the arraylist to add inferences into
     * @return an arraylist of inferences
     */
    private static ArrayList<Pair> inference(State csp, Point var, Integer value, ArrayList<Pair> steps) {
        Box box = csp.getBox(var);
        Point boxVar = new Point(box.getColIndex(), box.getRowIndex());
        Integer boxValue = value;

        // Restrict the boxes groups domains.
        box.set(true);
        box.getParentRow().restrictDomains(boxValue);
        box.getParentColumn().restrictDomains(boxValue);
        box.getParentBlock().restrictDomains(boxValue);

        // Check that the box has a valid placement.
        if(!box.checkValidity()) {
            // Unrestrict groups.
            box.set(false);
            box.getParentRow().unrestrictDomains(boxValue);
            box.getParentColumn().unrestrictDomains(boxValue);
            box.getParentBlock().unrestrictDomains(boxValue);

            // Exit case.
            return steps;
        }

        // Update the box.
        box.setNumber(boxValue);
        box.domainInference(boxValue);
        steps.add(new Pair(new Point(boxVar), boxValue));

        // Use MRV for more efficient group searching.
        Box newBox = findNearestLowestDomain(csp, box);

        // Check that a box could be inferred.
        if(newBox == null)
            return steps;
        else
            box = newBox;

        // Update the new box.
        boxVar.setLocation(box.getColIndex(), box.getRowIndex());
        boxValue = box.getDomain().get(0);

        // Run inference on the new box.
        return inference(csp, boxVar, boxValue, steps);
    }

    /**
     * Finds the smallest domain in a group.
     * @param csp the csp to reference
     * @param box the box to check the groups of
     * @return the box with the smallest domain.
     */
    private static Box findNearestLowestDomain(State csp, Box box) {
        int lowestDomain = 100;
        Box returnBox = box;

        // Iterate over all children of the boxes groups.
        for(int i = 0; i < 9; i++){
            Box curRowChild = box.getParentRow().getChild(i);
            Box curColChild = box.getParentColumn().getChild(i);
            Box curBlkChild = box.getParentBlock().getChild(i);

            // Check the rows for a minimum domain.
            if(curRowChild.getDomain().size() < lowestDomain && curRowChild.getNumber() == 0 && !curRowChild.isSet()){
                lowestDomain = curRowChild.getDomain().size();
                returnBox = curRowChild;
            }

            // Check the columns for a minimum domain.
            if(curColChild.getDomain().size() < lowestDomain && curColChild.getNumber() == 0 && !curColChild.isSet()){
                lowestDomain = curColChild.getDomain().size();
                returnBox = curColChild;
            }

            // Check the blocks for a minimum domain.
            if(curBlkChild.getDomain().size() < lowestDomain && curBlkChild.getNumber() == 0 && !curBlkChild.isSet()){
                lowestDomain = curBlkChild.getDomain().size();
                returnBox = curBlkChild;
            }
        }

        // Check that no unset domains are found.
        if(lowestDomain == 1)
            return returnBox;

        return null;
    }
}
