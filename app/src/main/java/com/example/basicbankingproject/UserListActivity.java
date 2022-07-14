package com.example.basicbankingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG = "user_list";
    List<CustomerModel> modelList_showlist = new ArrayList<>();
    RecyclerView mRecyclerView;
    String phonenumber;
    CustomerAdapter customerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showData();
    }

    private void showData() {
        Log.d(TAG, "showData: called");
        modelList_showlist.clear();
        Log.d(TAG, "showData: modellist cleared");
        Cursor cursor = new DatabaseHelper(this).readalldata();
        while(cursor.moveToNext()){

            Log.d(TAG, "showData: inside cursor");
            String balancefromdb = cursor.getString(2);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            CustomerModel model = new CustomerModel(cursor.getString(0), cursor.getString(5),cursor.getString(1), price);
            modelList_showlist.add(model);
        }
        customerAdapter=new CustomerAdapter(modelList_showlist,UserListActivity.this);
        mRecyclerView.setAdapter(customerAdapter);


    }
}