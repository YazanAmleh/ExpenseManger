package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.Model.DataSource;

public class ExpenseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new DataSource();
    }
}
