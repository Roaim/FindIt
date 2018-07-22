package com.roaimsapp.findit.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.roaimsapp.findit.Constants;
import com.roaimsapp.findit.data.model.Number;

import java.util.List;

/**
 * Created by Roaim on 21-Jul-18.
 */
@Dao
public interface NumberDao {
    @Insert
    long insert(Number number);

    @Update
    void update(Number... number);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId")
    List<Number> getAllNumbers(int segmentId);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId ORDER BY " + Constants.Database.COLUMN_ID + " DESC LIMIT 3")
    List<Number> getNumbers(int segmentId);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_NUMBER + " = :num ORDER BY " +
            Constants.Database.COLUMN_SEGMENT_ID)
    List<Number> getNumbers(String num);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_NUMBER + " = :num1 AND " +
            Constants.Database.COLUMN_NEXT_1 + " = :num2 ORDER BY " +
            Constants.Database.COLUMN_SEGMENT_ID)
    List<Number> getNumbers(String num1, String num2);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_NUMBER + " = :num1 AND " +
            Constants.Database.COLUMN_NEXT_1 + " = :num2 AND " +
            Constants.Database.COLUMN_NEXT_2 + " = :num3 ORDER BY " +
            Constants.Database.COLUMN_SEGMENT_ID)
    List<Number> getNumbers(String num1, String num2, String num3);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId AND " + Constants.Database.COLUMN_NUMBER + " = :num")
    List<Number> getNumbers(String num, int segmentId);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId AND " + Constants.Database.COLUMN_NUMBER + " = :num1 AND " +
            Constants.Database.COLUMN_NEXT_1 + " = :num2")
    List<Number> getNumbers(String num1, String num2, int segmentId);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId AND " + Constants.Database.COLUMN_NUMBER + " = :num1 AND " +
            Constants.Database.COLUMN_NEXT_1 + " = :num2 AND " +
            Constants.Database.COLUMN_NEXT_2 + " = :num3")
    List<Number> getNumbers(String num1, String num2, String num3, int segmentId);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId AND " + Constants.Database.COLUMN_ID + " > :id LIMIT 1")
    Number getNextNumber(int segmentId, int id);

    @Query("SELECT * FROM " + Constants.Database.TABLE_NUMBERS +
            " WHERE " + Constants.Database.COLUMN_SEGMENT_ID +
            " = :segmentId AND " + Constants.Database.COLUMN_ID + " < :id ORDER BY " +
            Constants.Database.COLUMN_ID + " DESC LIMIT 3")
    List<Number> getPrevNumbers(int segmentId, int id);
}
