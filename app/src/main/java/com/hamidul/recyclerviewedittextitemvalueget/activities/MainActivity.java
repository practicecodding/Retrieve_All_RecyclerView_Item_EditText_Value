package com.hamidul.recyclerviewedittextitemvalueget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.recyclerviewedittextitemvalueget.R;
import com.hamidul.recyclerviewedittextitemvalueget.adapters.MyAdapter;
import com.hamidul.recyclerviewedittextitemvalueget.model.OrderGS;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static Button button;
    MyAdapter myAdapter;
    ArrayList<OrderGS> arrayList;
    ItemTouchHelper itemTouchHelper;
    boolean notIsEmpty;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_title_layout);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViw);
        button = findViewById(R.id.button);

        getProduct();

        myAdapter = new MyAdapter(MainActivity.this,arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        /*recyclerView.setHasFixedSize(true);*/
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                arrayList.remove(position);
                myAdapter.notifyItemRemoved(position);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OrderGS item : arrayList){
                    if (!item.getEditTextValue().isEmpty()){
                        notIsEmpty = true;
                        break;
                    }
                    else {
                        notIsEmpty = false;
                    }
                }

                if (notIsEmpty){
                    MainActivity2.arrayList = arrayList;
                    startActivity(new Intent(MainActivity.this,MainActivity2.class));
                }
                else {
                    setToast("Please Input Quantity");
                }

            }

        });

    }



    //==============================================================================================

    void swipe(){
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                /**arrayList.remove(viewHolder.getAdapterPosition());
                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());*/
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(MainActivity.this, arrayList.get(viewHolder.getAdapterPosition()).getName()+" Successfully Deleted", Toast.LENGTH_SHORT).show();
                arrayList.remove(viewHolder.getAdapterPosition());
                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //==============================================================================================

    private void getProduct(){
        arrayList = new ArrayList<>();
        arrayList.add(new OrderGS(1,"Corn Flakes 120gm pp",""));
        arrayList.add(new OrderGS(2,"Corn Flakes 250gm pp",""));
        arrayList.add(new OrderGS(3,"Corn Flakes 250gm",""));
        arrayList.add(new OrderGS(4,"Corn Flakes 475gm",""));
        arrayList.add(new OrderGS(5,"Corn Flakes 1.1kg",""));
        arrayList.add(new OrderGS(6,"Cocos 127gm",""));
        arrayList.add(new OrderGS(7,"Cocos 250gm",""));
        arrayList.add(new OrderGS(8,"Cocos 385gm",""));
        arrayList.add(new OrderGS(9,"Cocos 700gm",""));
        arrayList.add(new OrderGS(10,"Cocos 1100gm",""));
        arrayList.add(new OrderGS(11,"Cocos Fills 250gm",""));
        arrayList.add(new OrderGS(12,"Muesli Fruit & Nut 500gm",""));
        arrayList.add(new OrderGS(13,"Muesli Fruit Magic 500gm",""));
        arrayList.add(new OrderGS(14,"Muesli Nut Delight 500gm",""));
        arrayList.add(new OrderGS(15,"Muesli No Added Sugar 500gm",""));
        arrayList.add(new OrderGS(16,"Fruit Loops 285gm",""));
        arrayList.add(new OrderGS(17,"Cocos Webs 300gm",""));
        arrayList.add(new OrderGS(18,"Special K 455gm",""));
        arrayList.add(new OrderGS(19,"Oats 400gm",""));
        arrayList.add(new OrderGS(20,"Oats 900gm",""));
        arrayList.add(new OrderGS(21,"Pringles Original 134gm",""));
        arrayList.add(new OrderGS(22,"Pringles Sour Cream Onion 134gm",""));
        arrayList.add(new OrderGS(23,"Pringles BBQ 134gm",""));
        arrayList.add(new OrderGS(24,"Pringles Hot & Spicy 134gm",""));
        arrayList.add(new OrderGS(25,"Pringles Cheesy Cheese 134gm",""));
        arrayList.add(new OrderGS(26,"Pringles Cheesy Cheese 42gm",""));
        arrayList.add(new OrderGS(27,"Pringles Original 42gm",""));
        arrayList.add(new OrderGS(28,"Pringles Sour Cream Onion 42gm",""));
    }

    //==============================================================================================

    private void setToast(String message){
        if (toast!=null) toast.cancel();
        toast = Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
        toast.show();
    }

}