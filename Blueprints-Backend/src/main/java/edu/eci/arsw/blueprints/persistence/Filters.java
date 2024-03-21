package edu.eci.arsw.blueprints.persistence;

import java.util.Set;

import org.springframework.stereotype.Service;
import edu.eci.arsw.blueprints.model.Blueprint;

public interface Filters {
    public void filterBlueprint(Blueprint bp) throws BlueprintNotFoundException;
    public void filterBlueprints(Set<Blueprint> bps) throws BlueprintNotFoundException;

}