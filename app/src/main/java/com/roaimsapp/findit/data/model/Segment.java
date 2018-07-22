package com.roaimsapp.findit.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.roaimsapp.findit.Constants;

/**
 * Created by Roaim on 21-Jul-18.
 */
@Entity(tableName = Constants.Database.TABLE_SEGMENTS)
public class Segment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Database.COLUMN_ID)
    private int id;
    @ColumnInfo(name = Constants.Database.COLUMN_NAME)
    private
    String name;

    public Segment(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
