package com.example.mehmood.splitbill.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int expenseId;
    private String expenseName;
    private String expenseAmount;
    private String expenseEventId;

    public Expense(String expenseName, String expenseAmount, String expenseEventId) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseEventId = expenseEventId;
    }
    @Ignore
    public Expense() {

    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseEventId() {
        return expenseEventId;
    }

    public void setExpenseEventId(String expenseEventId) {
        this.expenseEventId = expenseEventId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

}
