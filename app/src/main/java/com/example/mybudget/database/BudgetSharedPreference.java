package com.example.mybudget.database;

import android.content.Context;
import android.content.SharedPreferences;

public class BudgetSharedPreference {
    private final String PREF_NAME = "budget_preference";
    private final String KEY_TOTAL_BUDGET = "total_budget";
    private final String KEY_EXPECTED_BUDGET = "expected_budget";

    private SharedPreferences sharedPreferences;

    private static BudgetSharedPreference instance;

    public static BudgetSharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new BudgetSharedPreference(context);
        }
        return instance;
    }

    public BudgetSharedPreference(){}

    public BudgetSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveTotalBudget(long totalBudget) {
        this.sharedPreferences.edit().putLong(KEY_TOTAL_BUDGET, totalBudget).apply();
    }

    public long getTotalBudget() {
        return this.sharedPreferences.getLong(KEY_TOTAL_BUDGET, 0);
    }

    public void saveExpectedBudget(long expectedBudget) {
        this.sharedPreferences.edit().putLong(KEY_EXPECTED_BUDGET, expectedBudget).apply();
    }

    public long getExpectedBudget() {
        return this.sharedPreferences.getLong(KEY_EXPECTED_BUDGET, 0);
    }

    public void setExpectedBudget(long expectedBudget) {
        this.sharedPreferences.edit().putLong(KEY_EXPECTED_BUDGET, expectedBudget).apply();
    }

}
