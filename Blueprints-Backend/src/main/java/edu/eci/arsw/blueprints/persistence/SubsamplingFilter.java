package edu.eci.arsw.blueprints.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component
@Qualifier("SubsamplingFilter")
public class SubsamplingFilter implements Filters {

    public void subsampling(Blueprint bp) throws BlueprintNotFoundException {
        List<Point> points = new ArrayList<Point>(bp.getPoints());
        List<Point> pointsFilter = new ArrayList<Point>();
        for (int i = 0; i < points.size(); i++) {
            if (i % 2 == 0) {
                pointsFilter.add(points.get(i));
            }
        }
        bp.setPoints(pointsFilter);
    }

    @Override
    public void filterBlueprint(Blueprint bp) throws BlueprintNotFoundException {
        subsampling(bp);
    }

    @Override
    public void filterBlueprints(Set<Blueprint> bps) throws BlueprintNotFoundException {
        for (Blueprint blueprint : bps) {
            filterBlueprint(blueprint);
        }
    }

}
