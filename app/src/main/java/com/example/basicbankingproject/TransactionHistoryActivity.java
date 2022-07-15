package com.example.basicbankingproject;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {
    RecyclerView transactionhistoryrecyclerview;
    TextView transactionhistory;

    List<CustomerModel> modelList_historylist = new ArrayList<>();
    TransactionHistoryAdapter transactionHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        transactionhistory = findViewById(R.id.transactionhistory);
        transactionhistoryrecyclerview = findViewById(R.id.transactionhistoryrecyclerview);
        transactionhistoryrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }

    private void showData() {
        Cursor cursor = new DatabaseHelper(this).readtransferdata();

        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(4);
            Double balance = Double.parseDouble(balancefromdb);
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);
            CustomerModel model = new CustomerModel(cursor.getString(2), cursor.getString(3), price, cursor.getString(1), cursor.getString(5));
            modelList_historylist.add(model);
        }
        transactionHistoryAdapter = new TransactionHistoryAdapter(this, modelList_historylist);
        transactionhistoryrecyclerview.setAdapter(transactionHistoryAdapter);
        if (modelList_historylist.size() == 0) {
            transactionhistory.setVisibility(View.VISIBLE);
        }

    }
}
