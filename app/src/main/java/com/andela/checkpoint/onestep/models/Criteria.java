package com.andela.checkpoint.onestep.models;

import java.util.Date;
import java.util.List;

/**
 * Created by andela-jugba on 11/3/15.
 */
public interface Criteria {
    List<Location> meetCriteria(List<Location> locations, String date);
}
