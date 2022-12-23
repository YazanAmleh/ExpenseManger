package com.example.myapplication.Model;

//import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class Data {
    //extends RecyclerView.ViewHolder
    private int amount;
    private String type;
    private String note;
    private String id;
    private String date;

    public Data(int amount, String type, String note, String id, String date) {
        this.amount = amount;
        this.type = type;
        this.note = note;
        this.id = id;
        this.date = date;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        Data data = (Data) o;
        return amount == data.amount &&
                Objects.equals(type, data.type) &&
                Objects.equals(note, data.note) &&
                Objects.equals(id, data.id) &&
                Objects.equals(date, data.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, type, note, id, date);
    }
}
