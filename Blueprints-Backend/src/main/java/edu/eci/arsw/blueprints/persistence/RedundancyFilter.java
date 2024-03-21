package edu.eci.arsw.blueprints.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component
@Qualifier("RedundancyFilter")
public class RedundancyFilter implements Filters {

    public void removePoints(Blueprint bp, Point point) {
        List<Point> points = new ArrayList<Point>(bp.getPoints());
        for (int i = 0; i <= points.size() -1 ; i++) {
            if (point.equals(points.get(i))) {
                points.remove(point);
            }
        }
        points.add(point);
        bp.setPoints(points);
    }

    @Override
    public void filterBlueprint(Blueprint bp) throws BlueprintNotFoundException {
        for (Point point : bp.getPoints()) {
            removePoints(bp, point);
        }
    }

    @Override
    public void filterBlueprints(Set<Blueprint> blueprints)  throws BlueprintNotFoundException  {
        for (Blueprint blueprint : blueprints) {
            filterBlueprint(blueprint);
        }
    }
}