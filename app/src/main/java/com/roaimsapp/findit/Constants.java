package com.roaimsapp.findit;

/**
 * Created by Roaim on 21-Jul-18.
 */

public interface Constants {
    interface Database {
        String DATABASE_NAME = "number_db";
        int DATABASE_VERSION = 1;

        String COLUMN_ID = "id";

        String TABLE_SEGMENTS = "segments";
        String COLUMN_NAME = "name";

        String TABLE_NUMBERS = "numbers";
        String COLUMN_SEGMENT_ID = "segment_id";
        String COLUMN_NUMBER = "number";
        String COLUMN_PREV = "prev";
        String COLUMN_NEXT_1 = "next1";
        String COLUMN_NEXT_2 = "next2";
        String COLUMN_NEXT_3 = "next3";
        String COLUMN_NEXT_4 = "next4";
    }
}
