package com.andela.checkpoint.onestep.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andela-jugba on 11/3/15.
 */
public class DateCriterion implements Criteria {

    @Override
    public List<Location> meetCriteria(List<Location> locations, String date) {
        List<Location> filteredLocations = new ArrayList<>();
        for (Location location : locations) {
            DateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMM,yyyy");
            String temp = dateFormat.format(location.getDate());
            if (temp.equals(date)) {
                filteredLocations.add(location);
            }
        }
        return filteredLocations;
    }
}

