package com.hamidul.recyclerviewedittextitemvalueget.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.recyclerviewedittextitemvalueget.R;
import com.hamidul.recyclerviewedittextitemvalueget.model.Order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    public static ArrayList<Order> arrayList;
    ArrayList<Order> orders;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    LinearLayout totalAmountLayout, discountAmountLayout, netAmountLayout;
    TextView tvTotalAmount, tvDiscountAmount, tvNetAmount, tvNetAmountName;
    double sum,sumKellogg,sumPringles,sumDiscount;
    Toast toast;
    ItemTouchHelper itemTouchHelper;
    final static int REQUEST_CODE_STORAGE_PERMISSION = 1235;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recyclerViw);
        totalAmountLayout = findViewById(R.id.totalAmountLayout);
        discountAmountLayout = findViewById(R.id.discountAmountLayout);
        netAmountLayout = findViewById(R.id.netAmountLayout);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvDiscountAmount = findViewById(R.id.tvDiscountAmount);
        tvNetAmount = findViewById(R.id.tvNetAmount);
        tvNetAmountName = findViewById(R.id.tvNetAmountName);

        orders = new ArrayList<>();
        for (Order item : arrayList){
            if (!item.getQuantity().isEmpty()){
                orders.add(item);
            }
        }

        if (orders.size()>1){
            getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU's )");
        }
        else {
            getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU )");
        }

        upDatePrice();

        netAmountLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view = LayoutInflater.from(MainActivity2.this).inflate(R.layout.price_dialog,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setView(view);

                Button DP = view.findViewById(R.id.DP);
                Button TP = view.findViewById(R.id.TP);

                final AlertDialog dialog = builder.create();

                DP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        discountAmountLayout.setVisibility(View.GONE);
                        totalAmountLayout.setVisibility(View.GONE);

                        sumKellogg = 0;
                        sumPringles = 0;
                        for (int i=0; i<orders.size(); i++){
                            int quantity = Integer.parseInt(orders.get(i).getQuantity());
                            if (orders.get(i).getName().contains("Pringles")){
                                int mrpPringles = orders.get(i).getPrice();
                                int productPringles = quantity*mrpPringles;
                                sumPringles = sumPringles + productPringles;
                            }
                            else {
                                int mrpKellogg = orders.get(i).getPrice();
                                int productKellogg = quantity*mrpKellogg;
                                sumKellogg = sumKellogg + productKellogg;
                            }
                        }
                        double totalKelloggTp = sumKellogg/1.15;
                        double totalPringlesTp = sumPringles/1.12;
                        double totalTp = totalKelloggTp+totalPringlesTp;
                        double totalDp = totalTp/1.05;
                        if (totalDp%1==0){
                            tvNetAmount.setText(String.format("%,.0f",totalDp));
                        }
                        else {
                            tvNetAmount.setText(String.format("%,.2f",totalDp));
                        }
                        tvNetAmountName.setText("Total DP Amount");
                        dialog.cancel();
                    }
                });

                TP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        discountAmountLayout.setVisibility(View.GONE);
                        totalAmountLayout.setVisibility(View.GONE);

                        sumKellogg = 0;
                        sumPringles = 0;
                        for (int i=0; i<orders.size(); i++){
                            int quantity = Integer.parseInt(orders.get(i).getQuantity());
                            if (orders.get(i).getName().contains("Pringles")){
                                int mrpPringles = orders.get(i).getPrice();
                                int productPringles = quantity*mrpPringles;
                                sumPringles = sumPringles + productPringles;
                            }
                            else {
                                int mrpKellogg = orders.get(i).getPrice();
                                int productKellogg = quantity*mrpKellogg;
                                sumKellogg = sumKellogg + productKellogg;
                            }
                        }
                        double totalKelloggTp = sumKellogg/1.15;
                        double totalPringlesTp = sumPringles/1.12;
                        double totalTp = totalKelloggTp+totalPringlesTp;
                        if (totalTp%1==0){
                            tvNetAmount.setText(String.format("%,.0f",totalTp));
                        }
                        else {
                            tvNetAmount.setText(String.format("%,.2f",totalTp));
                        }
                        tvNetAmountName.setText("Total TP Amount");
                        dialog.cancel();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                return false;
            }
        });

        myAdapter = new MyAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        /**DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());*/
        recyclerView.setLayoutManager(linearLayoutManager);
        /**recyclerView.addItemDecoration(dividerItemDecoration);*/
        recyclerView.setAdapter(myAdapter);

        swipe();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>{

        int doubleClick = 0;

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            return new myViewHolder(layoutInflater.inflate(R.layout.item_order,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

            holder.productName.setText(orders.get(position).getName());
            holder.tvDiscount.setText(String.format("%,.0f",orders.get(position).getDiscount()));
            if (orders.get(position).getTp()%1==0){
                holder.multiply.setText( orders.get(position).getQuantity()+" * "+String.format("%.0f",orders.get(position).getTp()) );
            }
            else {
                holder.multiply.setText( orders.get(position).getQuantity()+" * "+String.format("%.2f",orders.get(position).getTp()) );
            }
            if ( orders.get(position).getTp()*Double.parseDouble(orders.get(position).getQuantity())%1==0){
                holder.tvUnit.setText(String.format("%,.0f",Double.parseDouble(orders.get(position).getQuantity())*orders.get(position).getTp()));
            }
            else {
                holder.tvUnit.setText(String.format("%,.2f",Double.parseDouble(orders.get(position).getQuantity())*orders.get(position).getTp()));
            }

        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView productName,tvUnit,multiply,tvDiscount;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.productName);
                tvUnit = itemView.findViewById(R.id.tvUnit);
                multiply = itemView.findViewById(R.id.multiply);
                tvDiscount = itemView.findViewById(R.id.tvDiscount);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        discountDialogBottom(getAdapterPosition());

                        return false;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doubleClick++;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleClick = 0;
                            }
                        },500);

                        if (doubleClick==2){
                            discountDialogCenter(getAdapterPosition());
                        }


                    }
                });

            }
        }

    }

    void upDatePrice(){
        sum = 0;
        sumDiscount = 0;
        for (int i=0; i<orders.size(); i++){
            int quantity = Integer.parseInt(orders.get(i).getQuantity());
            double discount = orders.get(i).getDiscount();
            double tp = orders.get(i).getTp();
            double product = quantity*tp;
            sum = sum + product;
            sumDiscount = sumDiscount+discount;
        }
        tvDiscountAmount.setText(String.format("%,.0f",sumDiscount));
        if (sum%1==0){
            tvTotalAmount.setText(String.format("%,.0f",sum));
            tvNetAmount.setText(String.format("%,.0f",sum-sumDiscount));
        }
        else {
            tvTotalAmount.setText(String.format("%,.2f",sum));
            tvNetAmount.setText(String.format("%,.2f",sum-sumDiscount));
        }
    }

    void setToast(String message){
        if (toast!=null) toast.cancel();
        toast = Toast.makeText(MainActivity2.this,message,Toast.LENGTH_SHORT);
        toast.show();
    }

    void swipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                /**arrayList.remove(viewHolder.getAdapterPosition());
                 myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());*/
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                setToast(orders.get(viewHolder.getAdapterPosition()).getName()+" Successfully Deleted");
                orders.get(viewHolder.getAdapterPosition()).setQuantity("");
                orders.get(viewHolder.getAdapterPosition()).setDiscount(0);
                if (dialog!=null){
                    dialog.cancel();
                }
                orders.remove(viewHolder.getAdapterPosition());
                upDatePrice();
                discountAmountLayout.setVisibility(View.VISIBLE);
                totalAmountLayout.setVisibility(View.VISIBLE);
                tvNetAmountName.setText("Net Amount");
                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                if (orders.isEmpty()){
                    finish();
                }
                else if (orders.size()>1){
                    getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU's )");
                }
                else {
                    getSupportActionBar().setTitle("Your Order ( "+orders.size()+" SKU )");
                }

            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //************************************************************************************

    void discountDialogBottom(int getAdapterPosition){
        dialog = new Dialog(MainActivity2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);

        TextView tvDiscount = dialog.findViewById(R.id.tvDiscount);
        EditText edDiscount = dialog.findViewById(R.id.edDiscount);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);

        tvDiscount.setText(orders.get(getAdapterPosition).getName()+"\nTotal Discount");

        if (orders.get(getAdapterPosition).getDiscount()>0){
            edDiscount.setText(String.format("%.0f",orders.get(getAdapterPosition).getDiscount()));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String discount = edDiscount.getText().toString();

                if (discount.isEmpty()){
                    orders.get(getAdapterPosition).setDiscount(0);
                    myAdapter.notifyDataSetChanged();
                    upDatePrice();
                    dialog.cancel();
                }
                else {
                    if (Double.parseDouble(orders.get(getAdapterPosition).getQuantity())*orders.get(getAdapterPosition).getTp()<=Double.parseDouble(discount)){
                        setToast("Wrong Amount");
                    }
                    else {
                        orders.get(getAdapterPosition).setDiscount(Double.parseDouble(discount));
                        myAdapter.notifyDataSetChanged();
                        upDatePrice();
                        dialog.cancel();
                    }
                }



            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    //************************************************************************************

    void discountDialogCenter(int getAdapterPosition){
        View view = LayoutInflater.from(MainActivity2.this).inflate(R.layout.discount_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setView(view);
        EditText edDiscount = view.findViewById(R.id.edDiscount);
        Button btnOk = view.findViewById(R.id.btnOk);
        TextView tv = view.findViewById(R.id.tvDiscount);

        final AlertDialog alertDialog = builder.create();

        tv.setText(orders.get(getAdapterPosition).getName()+"\nDiscount Per Pcs");

        if (orders.get(getAdapterPosition).getDiscount()>0){
            int Quantity = Integer.parseInt(orders.get(getAdapterPosition).getQuantity());
            double Discount = orders.get(getAdapterPosition).getDiscount();
            if (Discount/Quantity%1==0){
                edDiscount.setText( String.format("%.0f",Discount/Quantity));
            }
            else {
                edDiscount.setText( String.format("%.2f",Discount/Quantity));
            }
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String discount = edDiscount.getText().toString();
                if (discount.isEmpty()){
                    orders.get(getAdapterPosition).setDiscount(0);
                    myAdapter.notifyDataSetChanged();
                    upDatePrice();
                    alertDialog.cancel();
                }
                else {

                    if (orders.get(getAdapterPosition).getTp()>Double.parseDouble(discount)){
                        orders.get(getAdapterPosition).setDiscount(Double.parseDouble(discount)*Integer.parseInt(orders.get(getAdapterPosition).getQuantity()));
                        myAdapter.notifyDataSetChanged();
                        upDatePrice();
                        alertDialog.cancel();
                    }
                    else {
                        setToast("Wrong Amount");
                    }

                }


            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    //************************************************************************************



}