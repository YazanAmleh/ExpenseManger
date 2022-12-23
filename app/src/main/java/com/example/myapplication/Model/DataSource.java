package com.example.myapplication.Model;

import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final ArrayList<Data> incomeDataArrayList = new ArrayList<>();

    private static final ArrayList<Data> expenseDataArrayList = new ArrayList<>();

    public DataSource() {
        incomeDataArrayList.add(new Data(200, "friend", "a friend who owe to me ", "2", "11-3-2022"));
        incomeDataArrayList.add(new Data(432, "recipe", "helllo", "3", "12-2-2022"));
        incomeDataArrayList.add(new Data(3124, "Main Job ", "salary", "4", "11-5-2022"));
        incomeDataArrayList.add(new Data(123, "Tip", "from kind person", "5", "11-21-2022"));

        expenseDataArrayList.add(new Data(200, "friend", "a friend who owe to me ", "2", "11-3-2022"));
        expenseDataArrayList.add(new Data(432, "recipe", "helllo", "3", "12-2-2022"));
        expenseDataArrayList.add(new Data(3124, "Main Job ", "salary", "4", "11-5-2022"));
        expenseDataArrayList.add(new Data(123, "Tip", "from kind person", "5", "11-21-2022"));

    }


    public static void addIncomeData(Data data) {
        incomeDataArrayList.add(data);
    }

    public static void updateExpenseData(Data oldData, int amount, String type, String note) {
        int index = expenseDataArrayList.indexOf(oldData);
        expenseDataArrayList.set(index, new Data(amount, type, note, oldData.getId(), getCurrentData()));
    }

    public static void removeIncomeData(Data data) {
        incomeDataArrayList.remove(data);
    }

    public static void addExpenseData(Data data) {
        expenseDataArrayList.add(data);
    }

    public static void updateIncomeData(Data oldData, int amount, String type, String note) {
        int index = incomeDataArrayList.indexOf(oldData);
        incomeDataArrayList.set(index, new Data(amount, type, note, oldData.getId(), getCurrentData()));
    }


    public static void removeExpenseData(Data data) {
        expenseDataArrayList.remove(data);
    }

    public static String getCurrentData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now().toString().substring(0, 10);
        }
        return null;
    }

    public static List<Data> getIncomeDataArrayList() {
        return incomeDataArrayList;
    }

    public static ArrayList<Data> getExpenseDataArrayList() {
        return expenseDataArrayList;
    }

    public static int getTotalExpenses() {
        int totalExpense = 0;
        for (Data data : expenseDataArrayList) {
            totalExpense += data.getAmount();
        }
        return totalExpense;
    }

    public static int getTotalIncomes() {
        int totalIncomes = 0;
        for (Data data : expenseDataArrayList) {
            totalIncomes += data.getAmount();
        }
        return totalIncomes;
    }

    public static int getBalance() {
        return getTotalIncomes() - getTotalExpenses();
    }
}
