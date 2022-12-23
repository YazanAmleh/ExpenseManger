package com.example.myapplication.Model;

import android.app.Application;

public class ExpenseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new DataSource();
    }
}
