package com.bagew.expensetracker2.views.utils;

import com.bagew.expensetracker2.views.models.Category;

import java.util.ArrayList;

public class Constants {
    public static String Income = "Income";
    public static String Expense = "Expense";

    public static ArrayList<Category> categories;

    public static int DAILY=0;
    public static int MONTHLY=1;
    public static int CALANDER=2;
    public static int SUMMARY=3;
    public static int NOTES=4;
    public static int SELECTED_TAB=0;
    public static int SELECTED_TAB_STATS=0;

    public static String SELECTED_STATS_TYPE= Constants.Income;

    public static void setCategories(){
        categories = new ArrayList<>();
        categories.add(new Category("Salary"));
        categories.add(new Category("business"));
        categories.add(new Category("investment"));
        categories.add(new Category("rent"));
        categories.add(new Category("loan"));
        categories.add(new Category("others"));
        categories.add(new Category("online"));
        categories.add(new Category("Given By Dad")); //God Tier
    }
}
