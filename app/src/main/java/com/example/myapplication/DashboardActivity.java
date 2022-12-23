package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Data;
import com.example.myapplication.Model.DataSource;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries = new ArrayList<BarChart>();
    final String[] date = new String[10000000];

    private FloatingActionButton fab_main;
    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    private IncomeExpenseDataAdapter incomeAdapter;
    private IncomeExpenseDataAdapter expenseAdapter;


    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean isOpen = false;
    private Animation FadOpen, FadClose, Rob, Rof;

    //Dashboard income and expense result
    private TextView totalIncomeResult;
    private TextView totalExpenseResult;
    private TextView totalBalanceResult;

    private RecyclerView mRecyclerIncome;
    private RecyclerView mRecyclerExpense;

    @Override
    protected void onStart() {
        super.onStart();
        incomeAdapter.submitList(DataSource.getIncomeDataArrayList());
        incomeAdapter.notifyDataSetChanged();

        expenseAdapter.submitList(DataSource.getExpenseDataArrayList());
        expenseAdapter.notifyDataSetChanged();


        buildChartBars();

        totalBalanceResult.setText(String.valueOf(DataSource.getBalance()));
        totalExpenseResult.setText(DataSource.getTotalExpenses() + "");
        totalIncomeResult.setText(DataSource.getTotalIncomes() + "");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_home_page);


        fab_main = findViewById(R.id.fb_main_lus_btn);
        fab_income_btn = findViewById(R.id.income_ft_btn);
        fab_expense_btn = findViewById(R.id.expense_ft_btn);
        fab_income_txt = findViewById(R.id.income_ft_text);
        fab_expense_txt = findViewById(R.id.expense_ft_text);

        //Tatal inc and exp

        totalIncomeResult = findViewById(R.id.income_set_result);
        totalExpenseResult = findViewById(R.id.expense_set_result);
        totalBalanceResult = findViewById(R.id.balance_set_result);

        //Recycler

        mRecyclerIncome = findViewById(R.id.recycler_income);
        mRecyclerExpense = findViewById(R.id.recycler_expense);

        FadOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        FadClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        Rob = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        Rof = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);


        barChart = findViewById(R.id.bar_chart);

        buildChartBars();


        totalBalanceResult.setText(String.valueOf(DataSource.getBalance()));
        totalExpenseResult.setText(DataSource.getTotalExpenses() + "");
        totalIncomeResult.setText(DataSource.getTotalIncomes() + "");

        fab_main.setOnClickListener(v -> {

            addData();

            if (isOpen) {
                fab_main.startAnimation(Rof);
                fab_income_btn.startAnimation(FadClose);

                fab_expense_btn.startAnimation(FadClose);

                fab_income_btn.setClickable(false);
                fab_expense_btn.setClickable(false);

                fab_income_txt.startAnimation(FadClose);
                fab_expense_txt.startAnimation(FadClose);
                fab_income_txt.setClickable(false);
                fab_expense_txt.setClickable(false);
                isOpen = false;

            } else {
                fab_main.startAnimation(Rob);
                fab_income_btn.startAnimation(FadOpen);

                fab_expense_btn.startAnimation(FadOpen);

                fab_income_btn.setClickable(true);
                fab_expense_btn.setClickable(true);

                fab_income_txt.startAnimation(FadOpen);
                fab_expense_txt.startAnimation(FadOpen);
                fab_income_txt.setClickable(true);
                fab_expense_txt.setClickable(true);
                isOpen = true;
            }

        });


        initToolbar();
        initDrawer();
        initReyclerViews();
    }

    private void initReyclerViews() {
        LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerIncome.setHasFixedSize(true);
        mRecyclerIncome.setLayoutManager(layoutManagerIncome);
        incomeAdapter = new IncomeExpenseDataAdapter(false);
        mRecyclerIncome.setAdapter(incomeAdapter);
        incomeAdapter.submitList(DataSource.getIncomeDataArrayList());


        LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerExpense.setHasFixedSize(true);
        mRecyclerExpense.setLayoutManager(layoutManagerExpense);
        expenseAdapter = new IncomeExpenseDataAdapter(false);
        mRecyclerExpense.setAdapter(expenseAdapter);
        expenseAdapter.submitList(DataSource.getExpenseDataArrayList());
    }

    private void buildChartBars() {
        getBarEntries();
        barDataSet = new BarDataSet(barEntries, "Expenses");
        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setText("Expenses Per Day");
        XAxis xval = barChart.getXAxis();
        xval.setDrawLabels(true);
        xval.setValueFormatter(new IndexAxisValueFormatter(date));
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.zoomIn();
        barChart.setScrollContainer(true);
        barChart.setClickable(false);
        barChart.setScaleEnabled(false);
        barDataSet.notifyDataSetChanged();
        barChart.notifyDataSetChanged();
        barChart.invalidate();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Expense Manager");
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.naView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                displaySelectedListener(item.getItemId());
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void displaySelectedListener(int itemId) {
        switch (itemId) {

            case R.id.dashboard:
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.income:
                Intent intent = new Intent(this, EditIncomeActivity.class);
                startActivity(intent);
                break;

            case R.id.expense:
                Intent intentt = new Intent(this, EditExpenseActivity.class);
                startActivity(intentt);
                break;

            case R.id.calculator:
                openCalculator();
                break;

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Do you really want to Logout?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void openCalculator() {
        Intent intent = new Intent();
        intent.setClassName("com.android.calculator2", "com.android.calculator2.Calculator");
        try {

            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "there's no calculators installed ", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBarEntries() {
        Log.d("ExpenseData", "Reading Data");
        barEntries = new ArrayList();
        float a = 1f;
        int a1 = 1;
        for (Data data : DataSource.getExpenseDataArrayList()) {
            date[a1] = data.getDate();
            float amm = data.getAmount();
            barEntries.add(new BarEntry(a, amm));
            a++;
            a1++;
        }

    }

    private void ftAnimation() {
        if (isOpen) {
            fab_main.startAnimation(Rof);
            fab_income_btn.startAnimation(FadClose);

            fab_expense_btn.startAnimation(FadClose);

            fab_income_btn.setClickable(false);
            fab_expense_btn.setClickable(false);

            fab_income_txt.startAnimation(FadClose);
            fab_expense_txt.startAnimation(FadClose);
            fab_income_txt.setClickable(false);
            fab_expense_txt.setClickable(false);
            isOpen = false;

        } else {
            fab_main.startAnimation(Rob);
            fab_income_btn.startAnimation(FadOpen);

            fab_expense_btn.startAnimation(FadOpen);

            fab_income_btn.setClickable(true);
            fab_expense_btn.setClickable(true);

            fab_income_txt.startAnimation(FadOpen);
            fab_expense_txt.startAnimation(FadOpen);
            fab_income_txt.setClickable(true);
            fab_expense_txt.setClickable(true);
            isOpen = true;

        }
    }

    private void addData() {
        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                expenseDataInsert();
            }
        });
    }

    public void incomeDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);

        View myviewm = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myviewm);
        AlertDialog dialog = mydialog.create();

        EditText edtAmount = myviewm.findViewById(R.id.amount_edt);
        EditText edtType = myviewm.findViewById(R.id.type_edt);
        EditText edtNote = myviewm.findViewById(R.id.note_edt);

        Button btnSave = myviewm.findViewById(R.id.btnSave);
        Button btnCancel = myviewm.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            String type = edtType.getText().toString().trim();
            String amount = edtAmount.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (TextUtils.isEmpty(amount)) {
                edtAmount.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(type)) {
                edtType.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(note)) {
                edtNote.setError("Required Field..");
                return;
            }
            try {
                DataSource.addIncomeData(new Data(Integer.parseInt(amount), type, note, System.currentTimeMillis() + "", DataSource.getCurrentData()));
                ftAnimation();
                incomeAdapter.submitList(DataSource.getIncomeDataArrayList());
                incomeAdapter.notifyDataSetChanged();

                totalBalanceResult.setText(String.valueOf(DataSource.getBalance()));
                totalExpenseResult.setText(DataSource.getTotalExpenses() + "");
                totalIncomeResult.setText(DataSource.getTotalIncomes() + "");

                dialog.dismiss();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Amount should be a number!", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void expenseDataInsert() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);

        View myviewm = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        mydialog.setView(myviewm);
        final AlertDialog dialog = mydialog.create();
        dialog.setCancelable(false);

        EditText edtAmount = myviewm.findViewById(R.id.amount_edt);
        EditText edtType = myviewm.findViewById(R.id.type_edt);
        EditText edtNote = myviewm.findViewById(R.id.note_edt);

        Button btnSave = myviewm.findViewById(R.id.btnSave);
        Button btnCancel = myviewm.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            String type = edtType.getText().toString().trim();
            String amount = edtAmount.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (TextUtils.isEmpty(amount)) {
                edtAmount.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(type)) {
                edtType.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(note)) {
                edtNote.setError("Required Field..");
                return;
            }

            try {
                DataSource.addExpenseData(new Data(Integer.parseInt(amount), type, note, System.currentTimeMillis() + "", DataSource.getCurrentData()));
                ftAnimation();
                expenseAdapter.submitList(DataSource.getExpenseDataArrayList());
                expenseAdapter.notifyDataSetChanged();
                totalBalanceResult.setText(String.valueOf(DataSource.getBalance()));
                totalExpenseResult.setText(DataSource.getTotalExpenses() + "");
                totalIncomeResult.setText(DataSource.getTotalIncomes() + "");
                buildChartBars();
                dialog.dismiss();


            } catch (NumberFormatException e) {
                Toast.makeText(this, "Amount should be a number!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            ftAnimation();
            dialog.dismiss();
        });
        dialog.show();
    }
}


