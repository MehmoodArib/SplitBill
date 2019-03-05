package com.example.mehmood.splitbill.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Expense {
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

}
