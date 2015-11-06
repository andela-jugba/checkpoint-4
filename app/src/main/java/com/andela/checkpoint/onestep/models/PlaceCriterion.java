package com.andela.checkpoint.onestep.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela-jugba on 11/5/15.
 */
public class PlaceCriterion implements Criteria {
    @Override
    public List<Location> meetCriteria(List<Location> locations, String place) {
        List<Location> filteredLocations = new ArrayList<>();
        for (Location location : locations) {
            String temp = location.getName();
            if (temp.equals(place)) {
                filteredLocations.add(location);
            }
        }
        return filteredLocations;

    }
}
