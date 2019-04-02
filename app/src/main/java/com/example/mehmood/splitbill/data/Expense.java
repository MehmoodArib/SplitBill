package com.example.mehmood.splitbill.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Expense implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private Integer expenseId;
    private String expenseName;
    private Double expenseAmount;
    private Integer expenseEventId;

    public Expense(String expenseName, Double expenseAmount, Integer expenseEventId) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseEventId = expenseEventId;
    }
    @Ignore
    public Expense() {
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Ignore
    protected Expense(Parcel in) {
        if (in.readByte() == 0) {
            expenseId = null;
        } else {
            expenseId = in.readInt();
        }
        expenseName = in.readString();
        if (in.readByte() == 0) {
            expenseAmount = null;
        } else {
            expenseAmount = in.readDouble();
        }
        if (in.readByte() == 0) {
            expenseEventId = null;
        } else {
            expenseEventId = in.readInt();
        }
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Integer getExpenseEventId() {
        return expenseEventId;
    }

    public void setExpenseEventId(Integer expenseEventId) {
        this.expenseEventId = expenseEventId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (expenseId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(expenseId);
        }
        dest.writeString(expenseName);
        if (expenseAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(expenseAmount);
        }
        if (expenseEventId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(expenseEventId);
        }
    }
}
