package search;

import map.State;
import resource.*;

import java.awt.Point;
import java.util.ArrayList;

public class BTS {

    public static ArrayList<Pair> backtrackingSearch(State csp){
        return backtrack(new ArrayList<>(), csp);
    }

    private static ArrayList<Pair> backtrack(ArrayList<Pair> assignment, State csp){
        if(csp.isComplete())
            return assignment;

        Point var = selectUnassignedVariable(csp, assignment);
        //System.out.println(var);
        ArrayList<Integer> domain = csp.getBox(var.x, var.y).getDomain();
        for(int i = 0; i < domain.size(); i++) {
            Pair newAssignment = new Pair(var, csp.getBox(var).getDomain().get(i));
            //csp.getBox(var).set(true);
            ArrayList<Pair> inferences = new ArrayList<>();

            if(csp.getBox(var).checkValidity()) {
                //assignment.add(newAssignment);

                ArrayList<Pair> steps = new ArrayList<>();
                inferences = inference(csp, var, csp.getBox(var).getDomain().get(i), steps);

                if(inferences != null) {
                    assignment.addAll(inferences);
                    System.out.println("Number of verified moves: " + assignment.size());
                    ArrayList<Pair> result = backtrack(assignment, csp);
                    
                    if(result != null)
                        return result;
                }
            }
            csp.getBox(var).set(false);
            assignment.remove(newAssignment);
            assignment.remove(inferences);
        }

        return null;
    }

// =======
//         for(int i = 0; i < domain.size(); i++){
//             Integer number = domain.get(i);
//             csp.getBox(var).setNumber(number);
//             ArrayList<Box> rowList = csp.getBox(var).getParentRow().restrictDomains(number);
//             ArrayList<Box> colList = csp.getBox(var).getParentColumn().restrictDomains(number);
//             ArrayList<Box> blkList = csp.getBox(var).getParentBlock().restrictDomains(number);
//             ArrayList<Pair> inferences = new ArrayList<>();
//             if(csp.getBox(var).checkValidity()){
//                 Pair varValue = new Pair(var, number);
//                 assignment.add(varValue);

//             }

//             //ArrayList<Pair> inferences = new ArrayList<>(inference(csp, var, i));
//         }
// >>>>>>> 74452434e9f88abd2944106acb63298d4125ce36

    private static Point selectUnassignedVariable(State state, ArrayList<Pair> assignment){
        ArrayList<Point> selections = new ArrayList<>();
        Point location = new Point();
        int minDomainSize = 100;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int domainSize = state.getBox(j, i).getDomain().size();
                location.setLocation(j, i);
                if(domainSize <= minDomainSize && domainSize > 0 && !state.getBox(j, i).isSet() && !parseAssignment(assignment, location)){
                    if(domainSize < minDomainSize){
                        selections.clear();
                        minDomainSize = domainSize;
                    }
                    selections.add(new Point(j, i));
                }
            }
        }

        if(selections.size() == 1)
            return selections.get(0);

        return tieBreaker(selections, state);
    }

    private static boolean parseAssignment(ArrayList<Pair> assignment, Point location){
        for(int i = 0; i < assignment.size(); i++){
            if(assignment.get(i).getLocation().equals(location))
                return true;
        }
        return false;
    }

    private static Point tieBreaker(ArrayList<Point> selections, State state){
        Point choice = new Point();
        Point point;
        Box box;
        int maxOpenBoxes = 0;
        for(int i = 0; i < selections.size(); i++){
            point = selections.get(i);
            int openBoxes = 0;
            box = state.getBox(point.x, point.y);
            openBoxes += box.getParentRow().isComplete() + box.getParentColumn().isComplete() + box.getParentBlock().isComplete();
            if(openBoxes > maxOpenBoxes) {
                maxOpenBoxes = openBoxes;
                choice.setLocation(point);
            }
        }
        return choice;
    }

    private static ArrayList<Pair> inference(State csp, Point var, Integer value, ArrayList<Pair> steps) {
        //ArrayList<Pair> steps = new ArrayList<>();
        Box box = csp.getBox(var);
        Point boxVar = new Point(box.getColIndex(), box.getRowIndex());
        Integer boxValue = value;
        System.out.println("Testing " + boxValue.intValue() + " at [" + boxVar.x + ", " + boxVar.y + "]");

        box.set(true);
        box.getParentRow().restrictDomains(boxValue);
        box.getParentColumn().restrictDomains(boxValue);
        box.getParentBlock().restrictDomains(boxValue);
        if(!box.checkValidity()) {
            box.set(false);
            box.getParentRow().unrestrictDomains(boxValue);
            box.getParentColumn().unrestrictDomains(boxValue);
            box.getParentBlock().unrestrictDomains(boxValue);
            return steps;
        }
        box.setNumber(boxValue);
        box.domainInference(boxValue);
        steps.add(new Pair(new Point(boxVar), boxValue));
        Box newBox = findNearestLowestDomain(csp, box);
        System.out.println(steps.size() + 1);
        //csp.printBoard();
        if(newBox == null){
            return steps;
        }
        else
            box = newBox;
        boxVar.setLocation(box.getColIndex(), box.getRowIndex());
        boxValue = box.getDomain().get(0);

        return inference(csp, boxVar, boxValue, steps);
    }

    private static Box findNearestLowestDomain(State csp, Box box){
        int lowestDomain = 100;
        Box returnBox = box;
        for(int i = 0; i < 9; i++){
            Box curRowChild = box.getParentRow().getChild(i);
            Box curColChild = box.getParentColumn().getChild(i);
            Box curBlkChild = box.getParentBlock().getChild(i);
            if(curRowChild.getDomain().size() < lowestDomain && curRowChild.getNumber() == 0 && !curRowChild.isSet()){
                lowestDomain = curRowChild.getDomain().size();
                returnBox = curRowChild;
            }
            if(curColChild.getDomain().size() < lowestDomain && curColChild.getNumber() == 0 && !curColChild.isSet()){
                lowestDomain = curColChild.getDomain().size();
                returnBox = curColChild;
            }
            if(curBlkChild.getDomain().size() < lowestDomain && curBlkChild.getNumber() == 0 && !curBlkChild.isSet()){
                lowestDomain = curBlkChild.getDomain().size();
                returnBox = curBlkChild;
            }
        }
        if(lowestDomain > 9) {
            System.out.println("ERROR: Something went wrong in findNearestLowestDomain()");

        }
        if(lowestDomain == 1){
            return returnBox;
        }
        return null;
    }

    /*private ArrayList<Pair> inference(State csp, Point var, int value) {
        ArrayList<Pair> assignments = new ArrayList<>();
        
        Box box = csp.getBox(var);
        Block blk = box.getParentBlock();
        Column col = box.getParentColumn();
        Row row = box.getParentRow();

        row.restrictDomains(value);
        col.restrictDomains(value);
        blk.restrictDomains(value);

        for(int i = 0; i < row.getChildren().size(); i++) {
            if(row.getChild(i).getDomain().size() == 1 && row.getChild(i).getNumber() == 0) {
                int x = row.getChild(i).getRowIndex();
                int y = row.getChild(i).getColIndex();
                Point newVar = new Point(x, y);
                int newValue = row.getChild(i).getDomain().get(0);
                Pair newPair = new Pair(newVar, newValue);

                for(int j = 0; j < row.getChildren().size(); j++) {
                    if(row.getChild(j).getNumber() == newValue) {
                        return null;
                    }
                }

                assignments.add(newPair);
            }
        }

        for(int i = 0; i < col.getChildren().size(); i++) {
            if(col.getChild(i).getDomain().size() == 1) {
                int x = col.getChild(i).getRowIndex();
                int y = col.getChild(i).getColIndex();
                Point newVar = new Point(x, y);
                int newValue = col.getChild(i).getDomain().get(0);
                Pair newPair = new Pair(newVar, newValue);
                
                inference(csp, newVar, newValue);

                for(int j = 0; j < col.getChildren().size(); j++) {
                    if(col.getChild(j).getNumber() == newValue) {
                        return null;
                    }
                }

                assignments.add(newPair);
            }
        }

        for(int i = 0; i < blk.getChildren().size(); i++) {
            if(blk.getChild(i).getDomain().size() == 1 || blk.getChild(i).getDomain().get(0) != ) {
                int x = blk.getChild(i).getRowIndex();
                int y = blk.getChild(i).getColIndex();
                Point newVar = new Point(x, y);
                int newValue = blk.getChild(i).getDomain().get(0);
                Pair newPair = new Pair(newVar, newValue);
                
                for(int j = 0; j < blk.getChildren().size(); j++) {
                    if(blk.getChild(j).getNumber() == newValue) {
                        return null;
                    }
                }

                assignments.add(newPair);
            }
        }
    }*/

}
