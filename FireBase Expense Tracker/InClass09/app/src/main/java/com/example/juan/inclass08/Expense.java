package com.example.juan.inclass08;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Juan on 6/20/2017.
 */

public class Expense implements Serializable {


    static String[] categories = {"Groceries","Invoice","Transportation","Shopping","Rent","Trips","Utilities","Other"};

    String name;
    String category;
    double amount;
    Date date;
    public String _id;

    public Expense() {
    }

    public Expense(String name, String category, double amount, Date date) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public static String[] getCategories() {
        return categories;
    }

    public static void setCategories(String[] categories) {
        Expense.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
