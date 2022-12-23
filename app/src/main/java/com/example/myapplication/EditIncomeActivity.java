package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Model.DataSource;

public class EditIncomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView incomeTotalSum;


    public IncomeExpenseDataAdapter incomeDataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);


        incomeTotalSum = findViewById(R.id.income_txt_result);
        recyclerView = findViewById(R.id.recycler_id_income);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        incomeDataAdapter = new IncomeExpenseDataAdapter(true);
        recyclerView.setAdapter(incomeDataAdapter);
        incomeDataAdapter.submitList(DataSource.getIncomeDataArrayList());

        incomeTotalSum.setText(DataSource.getTotalIncomes() + "");
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyDataChanged() {
        incomeDataAdapter.submitList(DataSource.getIncomeDataArrayList());
        incomeDataAdapter.notifyDataSetChanged();
        incomeTotalSum.setText(DataSource.getTotalIncomes() + "");
    }
}