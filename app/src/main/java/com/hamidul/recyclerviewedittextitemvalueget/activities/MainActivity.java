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
import com.hamidul.recyclerviewedittextitemvalueget.model.Order;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static Button button;
    MyAdapter myAdapter;
    ArrayList<Order> arrayList;
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

        getProducts();

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

                for (Order item : arrayList){
                    if (!item.getQuantity().isEmpty()){
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

    private void getProducts(){
        arrayList = new ArrayList<>();
        arrayList.add(new Order(1,"Corn Flakes 120g pp","",131,0,150));
        arrayList.add(new Order(2,"Corn Flakes 250g pp","",220,0,250));
        arrayList.add(new Order(3,"Corn Flakes 250g","",315,0,360));
        arrayList.add(new Order(4,"Corn Flakes 475g","",540,0,620));
        arrayList.add(new Order(5,"Corn Flakes 1.1kg","",1070,0,1230));
        arrayList.add(new Order(6,"Real Almond Honey Corn Flakes 345g","",480,0,550));
        arrayList.add(new Order(7,"Real Honey Corn Flakes 300g","",461,0,530));
        arrayList.add(new Order(8,"Strawberry Corn Flakes 300g","",435,0,500));
        arrayList.add(new Order(9,"Cocos 127g","",140,0,160));
        arrayList.add(new Order(10,"Chocos 250g","",305,0,350));
        arrayList.add(new Order(11,"Chocos 385g","",400,0,460));
        arrayList.add(new Order(12,"Chocos 700g","",785,0,899));
        arrayList.add(new Order(13,"Chocos 1100g","",1155,0,1325));
        arrayList.add(new Order(14,"Variety Pack 109g","",90,0,100));
        arrayList.add(new Order(15,"Chocos 22g","",17.50,0,20));
        arrayList.add(new Order(16,"Chocos Moon & Star 22g","",17.50,0,20));
        arrayList.add(new Order(17,"Chocos Fills 250g","",435,0,499));
        arrayList.add(new Order(18,"Muesli Fruit & Nut 500g","",870,0,999));
        arrayList.add(new Order(19,"Muesli Fruit Magic 500g","",870,0,999));
        arrayList.add(new Order(20,"Muesli Nut Delight 500g","",870,0,999));
        arrayList.add(new Order(21,"Muesli No Added Sugar 500g","",870,0,999));
        arrayList.add(new Order(22,"Fruit Loops 285g","",461,0,530));
        arrayList.add(new Order(23,"Cocos Webs 300g","",435,0,500));
        arrayList.add(new Order(24,"Special K 290g","",435,0,499));
        arrayList.add(new Order(25,"Special K 455g","",610,0,700));
        arrayList.add(new Order(26,"Oats 400g","",305,0,350));
        arrayList.add(new Order(27,"Oats 900g","",610,0,699));
        arrayList.add(new Order(28,"Pringles Original 134g","",313,0,350));
        arrayList.add(new Order(29,"Pringles Sour Cream Onion 134g","",313,0,350));
        arrayList.add(new Order(30,"Pringles BBQ 134g","",313,0,350));
        arrayList.add(new Order(31,"Pringles Hot & Spicy 134g","",313,0,350));
        arrayList.add(new Order(32,"Pringles Cheesy Cheese 134g","",313,0,350));
        arrayList.add(new Order(33,"Pringles Original 42g","",135,0,150));
        arrayList.add(new Order(34,"Pringles Sour Cream Onion 42g","",135,0,150));
        arrayList.add(new Order(35,"Pringles Cheesy Cheese 42g","",135,0,150));
    }

    //==============================================================================================

    private void setToast(String message){
        if (toast!=null) toast.cancel();
        toast = Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG);
        toast.show();
    }

}