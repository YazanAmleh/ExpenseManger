package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Model.DataSource;

public class EditExpenseActivity extends AppCompatActivity {


    private TextView expenseTotalSum;

    public IncomeExpenseDataAdapter expenseDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        expenseTotalSum = findViewById(R.id.expense_txt_result);
        RecyclerView recyclerView = findViewById(R.id.recycler_id_expense);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        expenseDataAdapter = new IncomeExpenseDataAdapter(true);
        recyclerView.setAdapter(expenseDataAdapter);
        expenseDataAdapter.submitList(DataSource.getExpenseDataArrayList());
        expenseTotalSum.setText(DataSource.getTotalExpenses() + "");

    }

    public void notifyDataChanged() {
        expenseDataAdapter.submitList(DataSource.getExpenseDataArrayList());
        expenseTotalSum.setText(DataSource.getTotalExpenses() + "");
        expenseDataAdapter.notifyDataSetChanged();

    }
}