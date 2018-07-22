package com.roaimsapp.findit.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.roaimsapp.findit.Constants;

/**
 * Created by Roaim on 21-Jul-18.
 */
@Entity(tableName = Constants.Database.TABLE_NUMBERS,
        foreignKeys = @ForeignKey(
                entity = Segment.class,
                parentColumns = Constants.Database.COLUMN_ID,
                childColumns = Constants.Database.COLUMN_SEGMENT_ID,
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = Constants.Database.COLUMN_NUMBER),
                @Index(value = Constants.Database.COLUMN_SEGMENT_ID)})
public class Number {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Database.COLUMN_ID)
    private int id;
    @ColumnInfo(name = Constants.Database.COLUMN_SEGMENT_ID)
    private int segmentId;
    @ColumnInfo(name = Constants.Database.COLUMN_NUMBER)
    private String number;
    @ColumnInfo(name = Constants.Database.COLUMN_PREV)
    private String prev;
    @ColumnInfo(name = Constants.Database.COLUMN_NEXT_1)
    private String next1;
    @ColumnInfo(name = Constants.Database.COLUMN_NEXT_2)
    private String next2;
    @ColumnInfo(name = Constants.Database.COLUMN_NEXT_3)
    private String next3;
    @Ignore
    private int serial;

    public Number() {}

    public Number(String textNumber) {
        setNumber(textNumber);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext1() {
        return next1;
    }

    public void setNext1(String next1) {
        this.next1 = next1;
    }

    public String getNext2() {
        return next2;
    }

    public void setNext2(String next2) {
        this.next2 = next2;
    }

    public String getNext3() {
        return next3;
    }

    public void setNext3(String next3) {
        this.next3 = next3;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "Number{" +
                "id=" + id +
                ", serial=" + serial +
                ", segmentId=" + segmentId +
                ", number='" + number + '\'' +
                ", prev='" + prev + '\'' +
                ", next1='" + next1 + '\'' +
                ", next2='" + next2 + '\'' +
                ", next3='" + next3 + '\'' +
                '}';
    }
}
