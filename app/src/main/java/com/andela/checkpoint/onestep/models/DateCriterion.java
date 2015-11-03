package com.andela.checkpoint.onestep.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class DateCriterion implements Criteria {

    @Override
    public List<Location> meetCriteria(List<Location> locations, Date date) {
        List<Location> filteredLocations = new ArrayList<>();
        for (Location location : locations) {
            if (location.getDate().equals(date)) {
                filteredLocations.add(location);
            }
        }
        return filteredLocations;
    }
}

