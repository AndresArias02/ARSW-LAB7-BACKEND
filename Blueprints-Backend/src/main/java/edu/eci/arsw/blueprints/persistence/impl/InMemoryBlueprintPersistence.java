/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("inMemoryBluePrintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {

        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        Blueprint bp1 = new Blueprint("andres", "andres_p1",pts);
        Blueprint bp2 = new Blueprint("andres", "andres_p2",pts);
        Blueprint bp3 = new Blueprint("Sebastian", "sebastian_p1",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.putIfAbsent(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBluePrint() throws BlueprintNotFoundException {
        Set<Blueprint> blueprintList = new HashSet<>();
        for(Tuple<String,String> key: blueprints.keySet()){
            blueprintList.add(blueprints.get(key));
        }
        return blueprintList;
    }

    @Override
    public Set<Blueprint> getBluePrintByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprintList = new HashSet<>();
        for(Tuple<String,String> key: blueprints.keySet()){
            if(blueprints.get(key).getAuthor().equals(author)){
                blueprintList.add(blueprints.get(key));
            }
        }
        return blueprintList;
    }

    @Override
    public void deleteBlueprint(String author, String blueprintName) throws BlueprintNotFoundException {
        Tuple<String, String> tuple = new Tuple<>(author,blueprintName);
        blueprints.remove(tuple);
    }



}
