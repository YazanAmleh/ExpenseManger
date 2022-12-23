package com.example.myapplication;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Data;
import com.example.myapplication.Model.DataSource;

import java.text.DateFormat;
import java.util.Date;

public class IncomeExpenseDataAdapter extends ListAdapter<Data, RecyclerView.ViewHolder> {

    private boolean isEdit = false;

    public IncomeExpenseDataAdapter(boolean isEdit) {
        super(new DiffUtil.ItemCallback<Data>() {
            @Override
            public boolean areItemsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.isEdit = isEdit;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isEdit) {
            return new EditIncomeDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.income_recycler_data, parent, false));
        } else {
            return new IncomeDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_income, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Data data = getCurrentList().get(position);

        if (holder instanceof IncomeDataViewHolder) {
            ((IncomeDataViewHolder) holder).typeTextView.setText(data.getType());
            ((IncomeDataViewHolder) holder).amountTextView.setText(String.valueOf(data.getAmount()));
            ((IncomeDataViewHolder) holder).dateTextView.setText(data.getDate());
        }
        if (holder instanceof EditIncomeDataViewHolder) {
            EditIncomeDataViewHolder editIncomeDataViewHolder = (EditIncomeDataViewHolder) holder;
            editIncomeDataViewHolder.discriptionTextView.setText(data.getNote());
            editIncomeDataViewHolder.amountTextView.setText(data.getAmount() + "");
            editIncomeDataViewHolder.typeTextView.setText(data.getType());
            editIncomeDataViewHolder.dateTextView.setText(data.getDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editIncomeDataViewHolder.updateData(data);
                }
            });

        }
    }
}

class IncomeDataViewHolder extends RecyclerView.ViewHolder {

    TextView typeTextView, amountTextView, dateTextView;

    public IncomeDataViewHolder(@NonNull View itemView) {
        super(itemView);
        typeTextView = itemView.findViewById(R.id.type_Income_ds);
        amountTextView = itemView.findViewById(R.id.amount_Income_ds);
        dateTextView = itemView.findViewById(R.id.date_Income_ds);
    }
}

class EditIncomeDataViewHolder extends RecyclerView.ViewHolder {

    TextView typeTextView, amountTextView, dateTextView, discriptionTextView;

    public EditIncomeDataViewHolder(@NonNull View itemView) {
        super(itemView.getRootView());
        typeTextView = itemView.findViewById(R.id.type_txt_income);
        amountTextView = itemView.findViewById(R.id.amount_txt_income);
        dateTextView = itemView.findViewById(R.id.date_txt_income);
        discriptionTextView = itemView.findViewById(R.id.note_txt_income);
    }

    public void updateData(Data oldData) {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(itemView.getContext());
        LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
        View myview = inflater.inflate(R.layout.update_data_item, null);
        mydialog.setView(myview);

        EditText edtAmount = myview.findViewById(R.id.amount_edt);
        EditText edtType = myview.findViewById(R.id.type_edt);
        EditText edtNote = myview.findViewById(R.id.note_edt);

        //Set data to edit text..
        edtType.setText(typeTextView.getText());
        edtType.setSelection(typeTextView.getText().length());

        edtNote.setText(discriptionTextView.getText());
        edtNote.setSelection(discriptionTextView.getText().length());

        edtAmount.setText(String.valueOf(amountTextView.getText()));
        edtAmount.setSelection(String.valueOf(amountTextView.getText()).length());

        Button btnUpdate = myview.findViewById(R.id.btn_upd_Update);
        Button btnDelete = myview.findViewById(R.id.btnuPD_Delete);

        AlertDialog dialog = mydialog.create();

        btnUpdate.setOnClickListener(v -> {
            String type = edtType.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

//                String mdAmount = String.valueOf(amount);
            String mdAmount = edtAmount.getText().toString().trim();

            int myAmount = Integer.parseInt(mdAmount);

            String mDate = DateFormat.getDateInstance().format(new Date());


            if (itemView.getContext() instanceof EditIncomeActivity) {
                DataSource.updateIncomeData(oldData, myAmount, type, note);
                EditIncomeActivity currentActivity = (EditIncomeActivity) itemView.getContext();
                currentActivity.notifyDataChanged();

            } else if (itemView.getContext() instanceof EditExpenseActivity) {
                DataSource.updateExpenseData(oldData, myAmount, type, note);
                EditExpenseActivity currentActivity = (EditExpenseActivity) itemView.getContext();
                currentActivity.notifyDataChanged();
            }

            dialog.dismiss();
        });

        btnDelete.setOnClickListener(v -> {
            if (itemView.getContext() instanceof EditIncomeActivity) {
                DataSource.removeIncomeData(oldData);

                EditIncomeActivity currentActivity = (EditIncomeActivity) itemView.getContext();

                currentActivity.notifyDataChanged();

            } else if (itemView.getContext() instanceof EditExpenseActivity) {
                DataSource.removeExpenseData(oldData);

                EditExpenseActivity currentActivity = (EditExpenseActivity) itemView.getContext();

                currentActivity.notifyDataChanged();
            }

            dialog.dismiss();
        });
        dialog.show();
    }

}
