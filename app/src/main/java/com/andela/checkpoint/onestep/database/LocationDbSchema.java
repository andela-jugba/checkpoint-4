package com.andela.checkpoint.onestep.database;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationDbSchema {
    public static final class LocationTable {
        public static final String NAME = "locations";

        public static final class Col {
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String LONG = "long";
            public static final String LAT = "lat";
            public static final String UUID = "uuid";
            public static final String TIMES_VISITED = "times_visited";
        }
    }

}
