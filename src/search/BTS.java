package search;

import map.State;
import resource.Box;
import resource.Pair;

import java.awt.Point;
import java.util.ArrayList;

public class BTS {

    public ArrayList<Pair> backtrackingSearch(State csp){
        return backtrack(new ArrayList<>(), csp);
    }

    private ArrayList<Pair> backtrack(ArrayList<Pair> assignment, State csp){
        if(csp.isComplete()){
            return assignment;
        }
        Point var = selectUnassignedVariable(csp);
        ArrayList<Integer> domain = csp.getBox(var.x, var.y).getDomain();
        for(int i = 0; i < domain.size(); i++){
            csp.getBox(var).setNumber(domain.get(i));
            if()
        }
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

}
