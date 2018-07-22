package com.roaimsapp.findit.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.roaimsapp.findit.Constants;
import com.roaimsapp.findit.data.model.Segment;

import java.util.List;

/**
 * Created by Roaim on 21-Jul-18.
 */
@Dao
public interface SegmentDao {
    @Query("SELECT * FROM " + Constants.Database.TABLE_SEGMENTS)
    List<Segment> getAll();

    @Query("SELECT * FROM " + Constants.Database.TABLE_SEGMENTS + " WHERE " + Constants.Database.COLUMN_ID + " == :id LIMIT 1")
    Segment get(int id);

    @Insert
    long insert(Segment segment);

    @Delete
    void delete(Segment segment);
}
