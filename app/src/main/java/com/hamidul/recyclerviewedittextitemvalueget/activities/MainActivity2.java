package com.hamidul.recyclerviewedittextitemvalueget.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.recyclerviewedittextitemvalueget.R;
import com.hamidul.recyclerviewedittextitemvalueget.model.Order;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    public static ArrayList<Order> arrayList;
    ArrayList<Order> orders;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyclerViw);

        orders = new ArrayList<>();
        for (Order item : arrayList){
            if (!item.getEditTextValue().isEmpty()){
                orders.add(item);
            }
        }

        if (orders.size()>1){
            getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU's )");
        }
        else {
            getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU )");
        }

        myAdapter = new MyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(myAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>{

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = getLayoutInflater();

            return new myViewHolder(layoutInflater.inflate(R.layout.item_order,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

            holder.productName.setText(orders.get(position).getName());
            holder.tvUnit.setText(orders.get(position).getEditTextValue());

        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView productName,tvUnit;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.productName);
                tvUnit = itemView.findViewById(R.id.tvUnit);

            }
        }

    }

}