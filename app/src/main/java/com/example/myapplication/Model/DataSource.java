package com.example.myapplication.Model;

import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private static final ArrayList<Data> incomeDataArrayList = new ArrayList<>();

    private static final ArrayList<Data> expenseDataArrayList = new ArrayList<>();

    public DataSource() {
        incomeDataArrayList.add(new Data(200, "friend", "a friend who was owe to me ", "1", "2022-3-11"));
        incomeDataArrayList.add(new Data(432, "secondary job", "secondary job", "2", "2022-12-2"));
        incomeDataArrayList.add(new Data(3124, "Main Job ", "Main Job", "3", "2022-12-3"));
        incomeDataArrayList.add(new Data(123, "Tip", "from kind person", "4", "2022-12-11"));

        expenseDataArrayList.add(new Data(70, "bills", "electricity, water ", "1", "2022-11-1"));
        expenseDataArrayList.add(new Data(100, "new shoes", "Nike shoes", "2", "2022-11-2"));
        expenseDataArrayList.add(new Data(159, "new T-shirt ", "puma T-shirt", "3", "2022-11-3"));

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
        for (Data data : incomeDataArrayList) {
            totalIncomes += data.getAmount();
        }
        return totalIncomes;
    }

    public static int getBalance() {
        return getTotalIncomes() - getTotalExpenses();
    }
}
