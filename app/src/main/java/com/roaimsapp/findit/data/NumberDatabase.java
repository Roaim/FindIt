package com.roaimsapp.findit.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.roaimsapp.findit.Constants;
import com.roaimsapp.findit.data.dao.NumberDao;
import com.roaimsapp.findit.data.dao.SegmentDao;
import com.roaimsapp.findit.data.model.Number;
import com.roaimsapp.findit.data.model.Segment;

@Database(entities = {Segment.class, Number.class}, version = Constants.Database.DATABASE_VERSION, exportSchema = false)
public abstract class NumberDatabase extends RoomDatabase {
    public abstract SegmentDao segmentDao();
    public abstract NumberDao numberDao();

    private static NumberDatabase mInstance;

    public static NumberDatabase getDatabase(Context context) {
        if (mInstance == null) {
            synchronized (NumberDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NumberDatabase.class,
                            Constants.Database.DATABASE_NAME)
                            .build();
                }
            }
        }
        return mInstance;
    }
}