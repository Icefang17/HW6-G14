package search;

import map.State;
import resource.Block;
import resource.Box;
import resource.Column;
import resource.Pair;
import resource.Row;

import java.awt.Point;
import java.util.ArrayList;

public class BTS {

    public ArrayList<Pair> backtrackingSearch(State csp){
        return backtrack(new ArrayList<>(), csp);
    }

    private ArrayList<Pair> backtrack(ArrayList<Pair> assignment, State csp){
        if(csp.isComplete())
            return assignment;

        Point var = selectUnassignedVariable(csp);

        ArrayList<Integer> domain = csp.getBox(var.x, var.y).getDomain();
        for(int i = 0; i < domain.size(); i++) {
            Pair newAssignment = new Pair(var, i);
            ArrayList<Pair> inferences = new ArrayList<>();

            if(isConsistent(i, assignment, csp)) {
                assignment.add(new Pair(var, i));

                inferences.addAll(inference(csp, var, i));

                if(inferences != null) {
                    assignment.addAll(inferences);
                    ArrayList<Pair> result = backtrack(assignment, csp);
                    
                    if(result != null)
                        return result;
                }
            }
            assignment.remove(newAssignment);
            assignment.removeAll(inferences);
        }

        return null;
    }

    private Point selectUnassignedVariable(State state){
        ArrayList<Point> selections = new ArrayList<>();
        int minDomainSize = 100;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int domainSize = state.getBox(j, i).getDomain().size();
                if(domainSize <= minDomainSize && domainSize > 0){
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

    private Point tieBreaker(ArrayList<Point> selections, State state){
        Point choice = new Point();
        Point point;
        Box box;
        int maxOpenBoxes = 0;
        for(int i = 0; i < selections.size(); i++){
            point = selections.get(i);
            int openBoxes = 0;
            box = state.getBox(point.x, point.y);
            openBoxes += box.getParentRow().isComplete() + box.getParentColumn().isComplete() + box.getParentBlock().isComplete();
            if(openBoxes < maxOpenBoxes) {
                maxOpenBoxes = openBoxes;
                choice.setLocation(point);
            }
        }
        return choice;
    }

    private boolean isConsistent(int value, ArrayList<Pair> assignment, State csp) {
        
        
        return true;
    }

    private ArrayList<Pair> inference(State csp, Point var, int value) {
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
    }

}
