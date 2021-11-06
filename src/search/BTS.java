package search;

import map.State;
import resource.Box;
import resource.Pair;

import java.awt.Point;
import java.util.ArrayList;

public class BTS {

    public static ArrayList<Pair> backtrackingSearch(State csp){
        return backtrack(new ArrayList<>(), csp);
    }

    private static ArrayList<Pair> backtrack(ArrayList<Pair> assignment, State csp){
        if(csp.isComplete()){
            return assignment;
        }
        Point var = selectUnassignedVariable(csp);
        ArrayList<Integer> domain = csp.getBox(var.x, var.y).getDomain();
        for(int i = 0; i < domain.size(); i++){
            Integer number = domain.get(i);
            csp.getBox(var).setNumber(number);
            ArrayList<Box> rowList = csp.getBox(var).getParentRow().restrictDomains(number);
            ArrayList<Box> colList = csp.getBox(var).getParentColumn().restrictDomains(number);
            ArrayList<Box> blkList = csp.getBox(var).getParentBlock().restrictDomains(number);
            ArrayList<Pair> inferences = new ArrayList<>();
            if(csp.getBox(var).checkValidity()){
                Pair varValue = new Pair(var, number);
                assignment.add(varValue);

            }
        }
        return null;
    }

    private static Point selectUnassignedVariable(State state){
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
            if(openBoxes < maxOpenBoxes) {
                maxOpenBoxes = openBoxes;
                choice.setLocation(point);
            }
        }
        return choice;
    }

    private static ArrayList<Pair> inference(State csp, Point var, Integer value){
        ArrayList<Pair> steps = new ArrayList<>();
        boolean violation = false;
        while(violation == false){

        }
    }

}
